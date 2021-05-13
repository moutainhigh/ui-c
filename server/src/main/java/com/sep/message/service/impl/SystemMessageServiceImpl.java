package com.sep.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.enums.YesNo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.message.dto.*;
import com.sep.message.enums.BizErrorCode;
import com.sep.message.enums.SystemMessageStatus;
import com.sep.message.model.SystemMessage;
import com.sep.message.model.SystemMessageRecord;
import com.sep.message.repository.SystemMessageMapper;
import com.sep.message.service.SystemMessageRecordService;
import com.sep.message.service.SystemMessageService;
import com.sep.message.validator.PublishSystemMessageValidator;
import com.sep.message.validator.SuspendedSystemMessageValidator;
import com.sep.message.vo.MySystemMessageVo;
import com.sep.message.vo.SystemMessageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
public class SystemMessageServiceImpl extends ServiceImpl<SystemMessageMapper, SystemMessage> implements SystemMessageService {

    @Resource
    private SystemMessageRecordService systemMessageRecordService;
    @Resource
    private PublishSystemMessageValidator publishSystemMessageValidator;
    @Resource
    private SuspendedSystemMessageValidator suspendedSystemMessageValidator;

    @Override
    public IPage<SystemMessageVo> pageSearch(PageSearchSystemMessageDto dto) {
        IPage<SystemMessageVo> result = new Page<>();
        LambdaQueryWrapper<SystemMessage> lambda = new QueryWrapper<SystemMessage>().lambda();
        lambda.orderByDesc(SystemMessage::getCreateTime);
        if (Objects.nonNull(dto.getStatus())) {
            lambda.eq(SystemMessage::getStatus, dto.getStatus());
        }
        IPage<SystemMessage> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        List<SystemMessageVo> collect = page.getRecords().stream().map(systemMessage -> {
            SystemMessageVo vo = new SystemMessageVo();
            BeanUtils.copyProperties(systemMessage, vo);
            return vo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, result);
        result.setRecords(collect);
        return result;
    }

    @Override
    public boolean add(AddSystemMessageDto dto) {
        LocalDateTime now = LocalDateTime.now();
        SystemMessage message = new SystemMessage();
        message.setCreateUid(dto.getCreateUid());
        message.setUpdateUid(dto.getCreateUid());
        message.setCreateTime(now);
        message.setUpdateTime(now);
        message.setTitle(dto.getTitle());
        message.setContent(dto.getContent());
        message.setStatus(SystemMessageStatus.UNPUBLISHED.getCode());
        if (save(message)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_SAVE_ERROR);
    }

    @Override
    public boolean update(UpdateSystemMessageDto dto) {
        LocalDateTime now = LocalDateTime.now();
        SystemMessage message = new SystemMessage();
        message.setUpdateUid(dto.getUpdateUid());
        message.setUpdateTime(now);
        message.setTitle(dto.getTitle());
        message.setContent(dto.getContent());

        LambdaUpdateWrapper<SystemMessage> lambda = new UpdateWrapper<SystemMessage>().lambda();
        lambda.eq(SystemMessage::getId, dto.getId());
        lambda.eq(SystemMessage::getStatus, SystemMessageStatus.UNPUBLISHED.getCode());

        if (update(message, lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_UPDATE_ERROR);
    }

    @Transactional
    @Override
    public boolean publish(PublishSystemMessageDto dto) {
        publishSystemMessageValidator.validator(dto);
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<SystemMessage> lambda = new UpdateWrapper<SystemMessage>().lambda();
        lambda.set(SystemMessage::getStatus, SystemMessageStatus.PUBLISHED.getCode());
        lambda.set(SystemMessage::getUpdateUid, dto.getUpdateUid());
        lambda.set(SystemMessage::getUpdateTime, now);
        lambda.set(SystemMessage::getPublishTime, now);
        lambda.in(SystemMessage::getId, dto.getIds());
        lambda.eq(SystemMessage::getStatus, SystemMessageStatus.UNPUBLISHED.getCode());
        if (update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_PUBLISHED_ERROR);
    }

    @Transactional
    @Override
    public boolean suspended(SuspendedSystemMessageDto dto) {
        suspendedSystemMessageValidator.validator(dto);
        LambdaUpdateWrapper<SystemMessage> lambda = new UpdateWrapper<SystemMessage>().lambda();
        lambda.set(SystemMessage::getStatus, SystemMessageStatus.SUSPENDED.getCode());
        lambda.set(SystemMessage::getUpdateUid, dto.getUpdateUid());
        lambda.set(SystemMessage::getUpdateTime, LocalDateTime.now());
        lambda.in(SystemMessage::getId, dto.getIds());
        lambda.eq(SystemMessage::getStatus, SystemMessageStatus.PUBLISHED.getCode());
        if (update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_SUSPENDED_ERROR);
    }

    @Override
    public boolean delete(BaseUpdateDto dto) {
        LambdaUpdateWrapper<SystemMessage> lambda = new UpdateWrapper<SystemMessage>().lambda();
        lambda.set(SystemMessage::getIsDeleted, YesNo.YES.getCode());
        lambda.set(SystemMessage::getUpdateUid, dto.getUpdateUid());
        lambda.set(SystemMessage::getUpdateTime, LocalDateTime.now());
        lambda.eq(SystemMessage::getId, dto.getId());
        if (update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_DELETE_ERROR);
    }

    @Override
    public SystemMessageVo findById(Integer id) {
        SystemMessage systemMessage = getById(id);
        SystemMessageVo vo = new SystemMessageVo();
        BeanUtils.copyProperties(systemMessage, vo);
        return vo;
    }

    @Override
    public IPage<MySystemMessageVo> myMessages(MySystemMessageDto dto) {
        IPage<MySystemMessageVo> result = new Page<>();
        LambdaQueryWrapper<SystemMessage> lambda = new QueryWrapper<SystemMessage>().lambda();
        lambda.orderByDesc(SystemMessage::getPublishTime);
        lambda.eq(SystemMessage::getStatus, SystemMessageStatus.PUBLISHED.getCode());

        IPage<SystemMessage> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        List<Integer> ids = page.getRecords().stream().map(SystemMessage::getId).collect(Collectors.toList());
        LambdaQueryWrapper<SystemMessageRecord> recordLambda = new QueryWrapper<SystemMessageRecord>().lambda();
        recordLambda.select(SystemMessageRecord::getMessageId);
        recordLambda.in(SystemMessageRecord::getMessageId, ids);
        recordLambda.eq(SystemMessageRecord::getUserId, dto.getUserId());
        Collection<SystemMessageRecord> systemMessageRecords = systemMessageRecordService.list(recordLambda);
        Set<Integer> records = systemMessageRecords.stream().map(SystemMessageRecord::getMessageId)
                .collect(Collectors.toSet());

        List<MySystemMessageVo> collect = page.getRecords().stream().map(systemMessage -> {
            MySystemMessageVo vo = new MySystemMessageVo();
            BeanUtils.copyProperties(systemMessage, vo);
            vo.setRead(records.contains(vo.getId()));
            return vo;
        }).collect(Collectors.toList());

        result.setRecords(collect);
        return result;
    }

    @Override
    public MySystemMessageVo readMessage(ReadMessageDto dto) {
        LambdaQueryWrapper<SystemMessageRecord> recordLambda = new QueryWrapper<SystemMessageRecord>().lambda();
        recordLambda.eq(SystemMessageRecord::getMessageId, dto.getId());
        recordLambda.eq(SystemMessageRecord::getUserId, dto.getUserId());
        int count = systemMessageRecordService.count(recordLambda);
        if (count < 1) {
            SystemMessageRecord record = new SystemMessageRecord();
            record.setMessageId(dto.getId());
            record.setUserId(dto.getUserId());
            if (!systemMessageRecordService.save(record)) {
                throw new SepCustomException(BizErrorCode.SYSTEM_MESSAGE_READ_ERROR);
            }
        }

        SystemMessage systemMessage = getById(dto.getId());
        MySystemMessageVo vo = new MySystemMessageVo();
        BeanUtils.copyProperties(systemMessage, vo);
        vo.setRead(true);
        return vo;
    }

    @Override
    public Boolean isRead(Integer userId) {

        Integer smCount = lambdaQuery().eq(SystemMessage::getStatus, SystemMessageStatus.PUBLISHED.getCode()).count();
        Integer smRCount = systemMessageRecordService.lambdaQuery().eq(SystemMessageRecord::getUserId, userId).count();
        if (smCount < smRCount) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}