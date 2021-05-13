package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.dto.*;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.enums.FundChangeType;
import com.sep.distribution.enums.WithdrawStatus;
import com.sep.distribution.model.Withdraw;
import com.sep.distribution.model.WithdrawSumAmount;
import com.sep.distribution.repository.WithdrawMapper;
import com.sep.distribution.service.AccountService;
import com.sep.distribution.service.WithdrawService;
import com.sep.distribution.validator.WithdrawApplyValidator;
import com.sep.distribution.vo.background.WithdrawPageSearchVo;
import com.sep.distribution.vo.xcx.WithdrawDetailsVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 提现表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class WithdrawServiceImpl extends ServiceImpl<WithdrawMapper, Withdraw> implements WithdrawService {

    @Resource
    private WithdrawMapper withdrawMapper;
    @Resource
    private AccountService accountService;
    @Resource
    private WithdrawApplyValidator validator;
    @Resource
    private WxUserService wxUserService;

    @Override
    public Boolean apply(WithdrawApplyDto dto) {
        validator.validator(dto);
        Withdraw withdraw = new Withdraw();
        BeanUtils.copyProperties(dto, withdraw);
        withdraw.setCreateTime(LocalDateTime.now());
        withdraw.setUpdateTime(LocalDateTime.now());
        withdraw.setState(WithdrawStatus.WAITCONFIRM.getCode());
        boolean result = this.save(withdraw);
        if (!result) {
            throw new SepCustomException(BizErrorCode.WITHDRAW_APPLY_ERROR);
        }
        return true;
    }

    @Override
    public IPage<WithdrawDetailsVo> pageSearch(SearchWithdrawDto dto) {
        LambdaQueryWrapper<Withdraw> wrapper = new QueryWrapper<Withdraw>().lambda();
        wrapper.eq(Withdraw::getUserId, dto.getUserId());
        wrapper.ge(Withdraw::getCreateTime, dto.getStartTime());
        wrapper.le(Withdraw::getCreateTime, dto.getStartEnd());
        wrapper.orderByDesc(Withdraw::getId);
        IPage<Withdraw> withdrawIPage = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), wrapper);
        List<WithdrawDetailsVo> vos = withdrawIPage.getRecords().stream().map(withdraw -> {
            WithdrawDetailsVo vo = new WithdrawDetailsVo();
            BeanUtils.copyProperties(withdraw, vo);
            return vo;
        }).collect(Collectors.toList());
        IPage<WithdrawDetailsVo> result = new Page<>();
        BeanUtils.copyProperties(withdrawIPage, result);
        result.setRecords(vos);
        return result;
    }

    @Override
    public WithdrawDetailsVo withdrawDetails(Integer id) {
        Withdraw withdraw = this.getById(id);
        WithdrawDetailsVo vo = new WithdrawDetailsVo();
        BeanUtils.copyProperties(withdraw, vo);
        return vo;
    }

    @Override
    public BigDecimal available(Integer userId) {
        BigDecimal balance = accountService.balance(userId);
        if (balance.equals(BigDecimal.ZERO)) {
            return balance;
        }
        LambdaQueryWrapper<Withdraw> wrapper = new QueryWrapper<Withdraw>().lambda();
        wrapper.eq(Withdraw::getUserId, userId);
        wrapper.eq(Withdraw::getState, WithdrawStatus.WAITCONFIRM.getCode());
        BigDecimal amount = withdrawMapper.sumAmount(wrapper);

        BigDecimal available = balance.subtract(amount);
        return available.doubleValue() > 0 ? available : BigDecimal.ZERO;
    }

    @Override
    public IPage<WithdrawPageSearchVo> pageSearch(WithdrawPageSearchDto dto) {
        IPage<WithdrawPageSearchVo> result = new Page<>();

        //获取用户ID
        Integer userId = dto.getUserId();
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            GetUserInput getUserInput = new GetUserInput();
            getUserInput.setUserId(dto.getUserId());
            getUserInput.setTelnum(dto.getPhone());
            UserOutput userOutput = wxUserService.getUser(getUserInput);
            if (Objects.isNull(userOutput) || Objects.isNull(userOutput.getId())) {
                return result;
            }
            userId = userOutput.getId();
        }

        //构建查询条件
        LambdaQueryWrapper<Withdraw> wrapper = new QueryWrapper<Withdraw>().lambda();
        wrapper.orderByDesc(Withdraw::getId);
        if (Objects.nonNull(userId)) {
            wrapper.eq(Withdraw::getUserId, userId);
        }
        if (StringUtils.isNotEmpty(dto.getName())) {
            wrapper.like(Withdraw::getName, dto.getName());
        }
        if (Objects.nonNull(dto.getAccountType())) {
            wrapper.eq(Withdraw::getAccountType, dto.getAccountType());
        }
        if (StringUtils.isNotEmpty(dto.getAccount())) {
            wrapper.eq(Withdraw::getAccount, dto.getAccount());
        }
        if (Objects.nonNull(dto.getState())) {
            wrapper.eq(Withdraw::getState, dto.getState());
        }
        if (Objects.nonNull(dto.getStartTime())) {
            wrapper.ge(Withdraw::getCreateTime, dto.getStartTime());
        }
        if (Objects.nonNull(dto.getStartEnd())) {
            wrapper.le(Withdraw::getCreateTime, dto.getStartEnd());
        }
        //执行查询
        IPage<Withdraw> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), wrapper);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //获取用户id集合
        List<Integer> userIds = page.getRecords().stream().map(Withdraw::getUserId).collect(Collectors.toList());
        //获取用户信息
        GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
        getUserByIdsInput.setUserIds(userIds);
        List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
        Map<Integer, UserOutput> userMap = userOutputs.stream().
                collect(Collectors.toMap(UserOutput::getId, userOutput -> userOutput));
        UserOutput emptyUser = new UserOutput();

        //执行结果转换
        List<WithdrawPageSearchVo> vos = page.getRecords().stream().map(withdraw -> {
            WithdrawPageSearchVo vo = new WithdrawPageSearchVo();
            BeanUtils.copyProperties(withdraw, vo);
            vo.setPhone(userMap.getOrDefault(withdraw.getUserId(), emptyUser).getTelnum());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(vos);
        return result;
    }

    @Transactional
    @Override
    public Boolean approve(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Withdraw> wrapper = new UpdateWrapper<Withdraw>().lambda();
        wrapper.set(Withdraw::getState, WithdrawStatus.APPROVE.getCode());
        wrapper.set(Withdraw::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Withdraw::getId, dto.getId());
        wrapper.eq(Withdraw::getState, WithdrawStatus.WAITCONFIRM.getCode());
        Withdraw withdraw = this.getById(dto.getId());
        boolean bool = this.update(wrapper) && accountService.subtraction(FundChangeType.WITHDRAW.getCode(),
                withdraw.getAmount(), withdraw.getUserId());
        if (bool) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.WITHDRAW_APPROVE_ERROR);
    }

    @Override
    public Boolean reject(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Withdraw> wrapper = new UpdateWrapper<Withdraw>().lambda();
        wrapper.set(Withdraw::getState, WithdrawStatus.REJECT.getCode());
        wrapper.set(Withdraw::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Withdraw::getId, dto.getId());
        wrapper.eq(Withdraw::getState, WithdrawStatus.WAITCONFIRM.getCode());
        if (this.update(wrapper)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.WITHDRAW_REJECT_ERROR);
    }

    @Override
    public BigDecimal totalWithdraw(Integer userId) {
        LambdaQueryWrapper<Withdraw> wrapper = new QueryWrapper<Withdraw>().lambda();
        wrapper.eq(Withdraw::getUserId, userId);
        wrapper.eq(Withdraw::getState, WithdrawStatus.APPROVE.getCode());
        BigDecimal amount = withdrawMapper.sumAmount(wrapper);
        if (amount == null) {
            return new BigDecimal(0);
        }
        return amount;
    }

}