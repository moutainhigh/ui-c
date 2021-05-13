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
import com.sep.message.dto.MyLetterDto;
import com.sep.message.dto.PageSearchLetterRecordDto;
import com.sep.message.dto.ReadLetterDto;
import com.sep.message.enums.BizErrorCode;
import com.sep.message.model.LetterRecord;
import com.sep.message.repository.LetterRecordMapper;
import com.sep.message.service.LetterRecordService;
import com.sep.message.service.SystemMessageService;
import com.sep.message.vo.LetterRecordVo;
import com.sep.message.vo.MyLetterRecordVo;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class LetterRecordServiceImpl extends ServiceImpl<LetterRecordMapper, LetterRecord> implements LetterRecordService {

    @Resource
    private WxUserService wxUserService;


    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    public IPage<LetterRecordVo> pageSearch(PageSearchLetterRecordDto dto) {
        IPage<LetterRecordVo> result = new Page<>();
        LambdaQueryWrapper<LetterRecord> lambda = new QueryWrapper<LetterRecord>().lambda();
        lambda.orderByDesc(LetterRecord::getCreateTime);
        IPage<LetterRecord> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        List<Integer> ids = page.getRecords().stream().map(LetterRecord::getUserId).collect(Collectors.toList());
        GetUserByIdsInput input = new GetUserByIdsInput();
        input.setUserIds(ids);
        List<UserOutput> userByIds = wxUserService.getUserByIds(input);
        Map<Integer, UserOutput> userMaps = userByIds.stream()
                .collect(Collectors.toMap(UserOutput::getId, userOutput -> userOutput));
        UserOutput defaultUser = new UserOutput();

        List<LetterRecordVo> collect = page.getRecords().stream().map(letterRecord -> {
            LetterRecordVo vo = new LetterRecordVo();
            BeanUtils.copyProperties(letterRecord, vo);
            UserOutput userOutput = userMaps.getOrDefault(vo.getUserId(), defaultUser);
            vo.setPhone(userOutput.getTelnum());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @Override
    public LetterRecordVo findById(Integer id) {
        LetterRecord letterRecord = getById(id);
        LetterRecordVo vo = new LetterRecordVo();
        BeanUtils.copyProperties(letterRecord, vo);
        GetUserInput input = new GetUserInput();
        input.setUserId(letterRecord.getUserId());
        UserOutput userOutput = wxUserService.getUser(input);
        if (Objects.nonNull(userOutput)) {
            vo.setPhone(userOutput.getTelnum());
        }
        return vo;
    }

    @Override
    public IPage<MyLetterRecordVo> myLetters(MyLetterDto dto) {
        IPage<MyLetterRecordVo> result = new Page<>();
        LambdaQueryWrapper<LetterRecord> lambda = new QueryWrapper<LetterRecord>().lambda();
        lambda.eq(LetterRecord::getUserId, dto.getUserId());
        lambda.eq(Objects.nonNull(dto.getTriggerType()), LetterRecord::getTriggerType, dto.getTriggerType());
        lambda.orderByDesc(LetterRecord::getCreateTime);

        IPage<LetterRecord> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        List<MyLetterRecordVo> collect = page.getRecords().stream().map(letterRecord -> {
            MyLetterRecordVo vo = new MyLetterRecordVo();
            BeanUtils.copyProperties(letterRecord, vo);
            vo.setRead(letterRecord.getReadStatus() == 1);
            vo.setPublishTime(letterRecord.getCreateTime());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @Override
    public Integer isRead(Integer userId) {
        Integer count = lambdaQuery().eq(LetterRecord::getUserId, userId).eq(LetterRecord::getReadStatus, YesNo.NO.getCode()).count();
        Boolean isRead = systemMessageService.isRead(userId);
        if (count > 0 || isRead) {
            return YesNo.YES.getCode();

        }
        return YesNo.NO.getCode();
    }

    @Override
    public MyLetterRecordVo readLetter(ReadLetterDto dto) {
        MyLetterRecordVo vo = new MyLetterRecordVo();
        LetterRecord letterRecord = this.getById(dto.getId());
        if (!dto.getUserId().equals(letterRecord.getUserId())) {
            return vo;
        }
        BeanUtils.copyProperties(letterRecord, vo);
        vo.setRead(letterRecord.getReadStatus() == 1);
        vo.setPublishTime(letterRecord.getCreateTime());
        if (!vo.isRead()) {
            LambdaUpdateWrapper<LetterRecord> lambda = new UpdateWrapper<LetterRecord>().lambda();
            lambda.set(LetterRecord::getReadStatus, YesNo.YES.getCode());
            lambda.set(LetterRecord::getUpdateTime, LocalDateTime.now());
            lambda.eq(LetterRecord::getId, dto.getId());
            if (!update(lambda)) {
                throw new SepCustomException(BizErrorCode.LETTER_READ_ERROR);
            }
        }
        return vo;
    }

}