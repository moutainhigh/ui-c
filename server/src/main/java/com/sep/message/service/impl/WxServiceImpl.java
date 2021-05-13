package com.sep.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Throwables;
import com.sep.common.utils.TemplateUtils;
import com.sep.message.model.LetterRecord;
import com.sep.message.model.LetterTemplate;
import com.sep.message.model.LetterWxRecord;
import com.sep.message.model.WxTemplate;
import com.sep.message.proxy.WxMessageSendResp;
import com.sep.message.proxy.WxProxy;
import com.sep.message.repository.LetterWxRecordMapper;
import com.sep.message.repository.WxTemplateMapper;
import com.sep.message.service.LetterRecordService;
import com.sep.message.service.WxService;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Slf4j
@Service
public class WxServiceImpl implements WxService {

    @Resource
    private WxProxy wxProxy;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private LetterRecordService letterRecordService;
    @Resource
    private LetterWxRecordMapper letterWxRecordMapper;
    @Resource
    private WxTemplateMapper wxTemplateMapper;

    @Async
    @Override
    public void sendMessage(LetterTemplate letterTemplate, List<LetterRecord> letterRecords, Map<String, Object> message) {
        //查询微信模板
        WxTemplate wxTemplate = findByTmpId(letterTemplate.getId());
        String msg = TemplateUtils.processLetterTemplate(wxTemplate.getFieldMapper(), message);
        Map<String, Object> wxMsg = JSON.parseObject(msg);

        //获取用户信息
        List<Integer> userIds = letterRecords.stream().map(LetterRecord::getUserId).collect(Collectors.toList());
        GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
        getUserByIdsInput.setUserIds(userIds);
        List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
        Map<Integer, UserOutput> userOutputMap = userOutputs.stream()
                .collect(Collectors.toMap(UserOutput::getId, userOutput -> userOutput));

        //发送短信
        letterRecords.stream()
                .filter(letterRecord -> userOutputMap.containsKey(letterRecord.getUserId()))
                .filter(letterRecord -> {
                    UserOutput userOutput = userOutputMap.get(letterRecord.getUserId());
                    return StringUtils.isNotEmpty(userOutput.getTelnum());
                })
                .forEach(letterRecord -> {
                    UserOutput userOutput = userOutputMap.get(letterRecord.getUserId());
                    try {
                        WxMessageSendResp wxMessageSendResp =
                                wxProxy.messageSend(wxTemplate.getWxTemplateId(), userOutput.getOpenid(), wxMsg);
                        saveRecord(letterRecord, wxMessageSendResp.getErrcode() == 0,
                                wxMessageSendResp.getErrcode());
                        log.info("发送微信消息响应:" + wxMessageSendResp);
                    } catch (Exception e) {
                        log.error("发送微信消息异常:" + Throwables.getStackTraceAsString(e));
                    }
                });
    }

    private void saveRecord(LetterRecord letterRecord, boolean isSucceed, Integer code) {
        LetterWxRecord letterSmsRecord = new LetterWxRecord();
        BeanUtils.copyProperties(letterRecord, letterSmsRecord);
        letterSmsRecord.setLetterRecordId(letterRecord.getId());
        letterSmsRecord.setStatus(isSucceed);
        letterSmsRecord.setWxErrorCode(code);
        letterSmsRecord.setId(null);
        letterWxRecordMapper.insert(letterSmsRecord);
    }

    private WxTemplate findByTmpId(Integer tmpId) {
        LambdaQueryWrapper<WxTemplate> lambda = new QueryWrapper<WxTemplate>().lambda();
        lambda.eq(WxTemplate::getLetterTemplateId, tmpId);
        return wxTemplateMapper.selectOne(lambda);
    }

}