package com.sep.message.validator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sep.common.exceptions.SepCustomException;
import com.sep.message.dto.PublishSystemMessageDto;
import com.sep.message.enums.BizErrorCode;
import com.sep.message.enums.SystemMessageStatus;
import com.sep.message.model.SystemMessage;
import com.sep.message.service.SystemMessageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PublishSystemMessageValidator {

    @Resource
    private SystemMessageService systemMessageService;

    public void validator(PublishSystemMessageDto dto) {
        LambdaQueryWrapper<SystemMessage> lambda = new QueryWrapper<SystemMessage>().lambda();
        lambda.in(SystemMessage::getId, dto.getIds());
        lambda.ne(SystemMessage::getStatus, SystemMessageStatus.UNPUBLISHED.getCode());
        int count = systemMessageService.count(lambda);
        if (count > 0) {
            throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_PUBLISHED_STATUS_ERROR);
        }
    }

}