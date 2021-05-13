package com.sep.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.enums.YesNo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.TemplateUtils;
import com.sep.message.dto.DisableLetterTemplateDto;
import com.sep.message.dto.EnableLetterTemplateDto;
import com.sep.message.dto.PageSearchLetterTemplateDto;
import com.sep.message.enums.BizErrorCode;
import com.sep.message.dto.SendLetterInput;
import com.sep.message.model.LetterRecord;
import com.sep.message.model.LetterTemplate;
import com.sep.message.repository.LetterTemplateMapper;
import com.sep.message.service.LetterRecordService;
import com.sep.message.service.LetterTemplateService;
import com.sep.message.service.SmsService;
import com.sep.message.service.WxService;
import com.sep.message.vo.LetterTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Service
@Slf4j
public class LetterTemplateServiceImpl extends ServiceImpl<LetterTemplateMapper, LetterTemplate> implements LetterTemplateService {

    @Resource
    private LetterRecordService letterRecordService;
    @Resource
    private SmsService smsService;
    @Resource
    private WxService wxService;

    @Override
    public IPage<LetterTemplateVo> pageSearch(PageSearchLetterTemplateDto dto) {
        IPage<LetterTemplateVo> result = new Page<>();
        LambdaQueryWrapper<LetterTemplate> lambda = new QueryWrapper<LetterTemplate>().lambda();
        IPage<LetterTemplate> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }
        List<LetterTemplateVo> collect = page.getRecords().stream().map(letterTemplate -> {
            LetterTemplateVo vo = new LetterTemplateVo();
            BeanUtils.copyProperties(letterTemplate, vo);
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @Override
    public LetterTemplateVo findById(Integer id) {
        LetterTemplate letterTemplate = getById(id);
        LetterTemplateVo vo = new LetterTemplateVo();
        BeanUtils.copyProperties(letterTemplate, vo);
        return vo;
    }

    @Override
    public boolean enable(EnableLetterTemplateDto dto) {
        LambdaUpdateWrapper<LetterTemplate> lambda = new UpdateWrapper<LetterTemplate>().lambda();
        lambda.set(LetterTemplate::getUpdateTime, LocalDateTime.now());
        lambda.set(LetterTemplate::getUpdateUid, dto.getUpdateUid());
        lambda.set(LetterTemplate::getEnabledStatus, YesNo.NO.getCode());
        lambda.in(LetterTemplate::getId, dto.getIds());
        if (update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.LETTER_TEMPLATE_ENABLE_ERROR);
    }

    @Override
    public boolean disable(DisableLetterTemplateDto dto) {
        LambdaUpdateWrapper<LetterTemplate> lambda = new UpdateWrapper<LetterTemplate>().lambda();
        lambda.set(LetterTemplate::getUpdateTime, LocalDateTime.now());
        lambda.set(LetterTemplate::getUpdateUid, dto.getUpdateUid());
        lambda.set(LetterTemplate::getEnabledStatus, YesNo.YES.getCode());
        lambda.in(LetterTemplate::getId, dto.getIds());
        if (update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.LETTER_TEMPLATE_DISABLE_ERROR);
    }

    @Transactional
    @Override
    public boolean send(SendLetterInput dto) {
        log.info("开始调用消息发送.............................");
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<LetterTemplate> wrapper = new QueryWrapper<LetterTemplate>().lambda();
        wrapper.eq(LetterTemplate::getTriggerPoint, dto.getTriggerPoint());
        wrapper.eq(LetterTemplate::getEnabledStatus, YesNo.NO.getCode());
        LetterTemplate letterTemplate = getOne(wrapper);
        log.info("获取模板.............................{}",letterTemplate);
        if (Objects.isNull(letterTemplate)) {
            throw new SepCustomException(BizErrorCode.LETTER_TEMPLATE_NOT_FOUND_ERROR);
        }
        String message = TemplateUtils.processLetterTemplate(letterTemplate.getContent(), dto.getMessages());
        log.info("message.............................{}",message);
        List<LetterRecord> collect = dto.getUserIds().stream().map(userId -> {
            LetterRecord letterRecord = new LetterRecord();
            letterRecord.setCreateTime(now);
            letterRecord.setTitle(letterTemplate.getTitle());
            letterRecord.setContent(message);
            letterRecord.setReadStatus(YesNo.NO.getCode());
            letterRecord.setTriggerPoint(letterTemplate.getTriggerPoint());
            letterRecord.setTriggerType(letterTemplate.getTriggerType());
            letterRecord.setUserId(userId);
            letterRecord.setTemplateId(letterTemplate.getId());
            return letterRecord;
        }).collect(Collectors.toList());
        if (letterRecordService.saveBatch(collect)) {
            log.info("发送.............................{}",collect);
            dispatch(letterTemplate.getAlarmType(), letterTemplate, collect, dto.getMessages());
            return true;
        }
        throw new SepCustomException(BizErrorCode.SEND_LETTER_ERROR);
    }

    private void dispatch(String alarmType, LetterTemplate letterTemplate, List<LetterRecord> letterRecords,
                          Map<String, Object> message) {
        if (StringUtils.isEmpty(alarmType) || CollectionUtils.isEmpty(letterRecords)) {
            return;
        }

        Arrays.stream(alarmType.split(","))
                .filter(StringUtils::isNotEmpty)
                .map(Integer::valueOf).forEach(type -> {
            switch (type) {
                //短信
                case 1:
                    smsService.sendSms(letterTemplate, letterRecords, message);
                    break;
                //微信消息
                case 2:
                    // 微信消息
                    wxService.sendMessage(letterTemplate, letterRecords, message);
                    break;
                default:
                    break;
            }
        });

    }

}