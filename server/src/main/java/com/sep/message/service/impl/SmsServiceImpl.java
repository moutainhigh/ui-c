package com.sep.message.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.google.common.base.Throwables;
import com.sep.message.model.LetterRecord;
import com.sep.message.model.LetterSmsRecord;
import com.sep.message.model.LetterTemplate;
import com.sep.message.proxy.SmsAliyuncsProxy;
import com.sep.message.repository.LetterSmsRecordMapper;
import com.sep.message.service.LetterRecordService;
import com.sep.message.service.SmsService;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsAliyuncsProxy smsAliyuncsProxy;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private LetterRecordService letterRecordService;
    @Resource
    private LetterSmsRecordMapper letterSmsRecordMapper;
    @Value("${sms.provider}")
    private String provider;

    @Async
    @Override
    public void sendSms(LetterTemplate letterTemplate, List<LetterRecord> letterRecords, Map<String, Object> message) {

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
                        SendSmsResponse sendSmsResponse = smsAliyuncsProxy.sendSms(userOutput.getTelnum(),
                                letterTemplate.getSmsTemplateId(), message);
                        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                            //请求成功
                            saveRecord(letterRecord, true, sendSmsResponse.getCode(), sendSmsResponse.getBizId());
                        } else {
                            saveRecord(letterRecord, false, sendSmsResponse.getCode(), sendSmsResponse.getBizId());
                            log.error("短信发送错误:" + sendSmsResponse.getCode());
                        }
                    } catch (Exception e) {
                        log.error("短信发送异常:" + Throwables.getStackTraceAsString(e));
                    }
                });
    }

    private void saveRecord(LetterRecord letterRecord, boolean isSucceed, String code, String bizId) {
        LetterSmsRecord letterSmsRecord = new LetterSmsRecord();
        BeanUtils.copyProperties(letterRecord, letterSmsRecord);
        letterSmsRecord.setLetterRecordId(letterRecord.getId());
        letterSmsRecord.setStatus(isSucceed);
        letterSmsRecord.setSmsErrorCode(code);
        letterSmsRecord.setBizId(bizId);
        letterSmsRecord.setProvider(provider);
        letterSmsRecord.setId(null);
        letterSmsRecordMapper.insert(letterSmsRecord);
    }

}