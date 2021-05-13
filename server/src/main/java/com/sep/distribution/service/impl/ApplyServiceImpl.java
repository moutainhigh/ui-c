package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.distribution.dto.*;
import com.sep.distribution.enums.ApplyStatus;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.enums.WithdrawStatus;
import com.sep.distribution.model.*;
import com.sep.distribution.repository.ApplyMapper;
import com.sep.distribution.repository.WithdrawMapper;
import com.sep.distribution.service.AccountService;
import com.sep.distribution.service.ApplyService;
import com.sep.distribution.service.IdentityService;
import com.sep.distribution.vo.background.*;
import com.sep.distribution.vo.xcx.DistributionApplyDetailsVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.sku.bean.StatisticalOrderInfo;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.service.OrderService;
import com.sep.sku.vo.StatisticalOrderRespVo;
import com.sep.user.input.GetLowerInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.input.StatisticalUserLowerByIdsInput;
import com.sep.user.output.StatisticalUserLowerOutput;
import com.sep.user.output.StatisticalUserLowerRespOutput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 分销申请表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {

    @Resource
    private IdentityService identityService;
    @Resource
    private AccountService accountService;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private OrderService orderService;
    @Resource
    private WithdrawMapper withdrawMapper;

    @Override
    public Integer apply(DistributionApplyDto dto) {
        //对象转换
        Apply apply = new Apply();
        BeanUtils.copyProperties(dto, apply);
        apply.setUpdateTime(LocalDateTime.now());
        apply.setApplyState(ApplyStatus.WAITCONFIRM.getCode());
        //查询用户是否已提交申请
        LambdaQueryWrapper<Apply> query = new QueryWrapper<Apply>().lambda();
        query.eq(Apply::getUserId, dto.getUserId());
        int count = this.count(query);
        boolean result;
        //如果已提交则表示重新申请
        if (count > 0) {
            //只可以更新当前用户被驳回的申请
            LambdaUpdateWrapper<Apply> update = new UpdateWrapper<Apply>().lambda();
            update.eq(Apply::getUserId, dto.getUserId());
            update.eq(Apply::getApplyState, ApplyStatus.REJECT.getCode());
            result = this.update(apply, update);
        } else {
            //获取上级用户
            GetUserInput getUserInput = new GetUserInput();
            getUserInput.setUserId(dto.getUserId());
            UserOutput userOutput = wxUserService.getParentUser(getUserInput);
            if (Objects.nonNull(userOutput)) {
                apply.setInviteParentId(userOutput.getInviteParentId());
            }
            apply.setCreateUid(null);
            apply.setCreateTime(LocalDateTime.now());
            result = this.save(apply);
        }
        //判断申请是否成功¬
        if (!result) {
            throw new SepCustomException(BizErrorCode.DISTRIBUTION_APPLY_ERROR);
        }
        return apply.getId();
    }

    @Override
    public DistributionApplyDetailsVo getByUserId(Integer userId) {
        LambdaQueryWrapper<Apply> query = new QueryWrapper<Apply>().lambda();
        query.eq(Apply::getUserId, userId);
        Apply apply = this.getOne(query);
        DistributionApplyDetailsVo vo = new DistributionApplyDetailsVo();
        if (Objects.nonNull(apply)) {
            BeanUtils.copyProperties(apply, vo);
            return vo;
        }
        return null;
    }

    @Override
    public Optional<Integer> getIdentityByUserId(Integer userId) {
        LambdaQueryWrapper<Apply> query = new QueryWrapper<Apply>().lambda();
        query.eq(Apply::getUserId, userId);
        query.eq(Apply::getApplyState, ApplyStatus.APPROVE.getCode());
        query.select(Apply::getDistributionIdentityId);
        Apply apply = this.getOne(query);
        if (Objects.nonNull(apply)) {
            return Optional.of(apply.getDistributionIdentityId());
        }
        return Optional.empty();
    }

    @Override
    public IPage<ApplyPageSearchVo> pageSearch(ApplyPageSearchDto dto) {
        Page<ApplyPageSearchVo> result = new Page<>();
        //构建查询条件
        LambdaQueryWrapper<Apply> query = new QueryWrapper<Apply>().lambda();
        query.orderByDesc(Apply::getId);
        if (Objects.nonNull(dto.getUserId())) {
            query.eq(Apply::getUserId, dto.getUserId());
        }
        if (StringUtils.isNotEmpty(dto.getName())) {
            query.like(Apply::getName, dto.getName());
        }
        if (StringUtils.isNotEmpty(dto.getIdCard())) {
            query.eq(Apply::getIdCard, dto.getIdCard());
        }
        if (Objects.nonNull(dto.getDistributionIdentityId())) {
            query.eq(Apply::getDistributionIdentityId, dto.getDistributionIdentityId());
        }
        if (Objects.nonNull(dto.getApplyState())) {
            query.eq(Apply::getApplyState, dto.getApplyState());
        }

        //执行查询
        IPage<Apply> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), query);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //获取分销身份对象
        Set<Integer> identityIds = page.getRecords().stream()
                .map(Apply::getDistributionIdentityId).collect(Collectors.toSet());
        Collection<Identity> identities = identityService.listByIds(identityIds);
        Map<Integer, Identity> identityMap = identities.stream().collect(Collectors.toMap(Identity::getId, identity -> identity));

        //结果转换
        List<ApplyPageSearchVo> collect = page.getRecords().stream().map(apply -> {
            ApplyPageSearchVo vo = new ApplyPageSearchVo();
            BeanUtils.copyProperties(apply, vo);
            if (identityMap.containsKey(apply.getDistributionIdentityId())) {
                vo.setDistributionIdentity(identityMap.get(apply.getDistributionIdentityId()).getName());
            }
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @Transactional
    @Override
    public boolean approve(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Apply> wrapper = new UpdateWrapper<Apply>().lambda();
        wrapper.set(Apply::getApplyState, ApplyStatus.APPROVE.getCode());
        wrapper.set(Apply::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Apply::getId, dto.getId());
        wrapper.eq(Apply::getApplyState, ApplyStatus.WAITCONFIRM.getCode());
        Apply apply = this.getById(dto.getId());

        //查询用户信息
        GetUserInput getUserInput = new GetUserInput();
        getUserInput.setUserId(apply.getUserId());
        UserOutput user = wxUserService.getUser(getUserInput);
        wrapper.set(Apply::getInviteParentId, user.getInviteParentId());

        //创建帐号
        AccountAddDto accountAddDto = new AccountAddDto();
        accountAddDto.setCreateUid(dto.getUpdateUid());
        accountAddDto.setUserId(apply.getUserId());
        if (this.update(wrapper) && accountService.add(accountAddDto)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.APPLY_APPROVE_ERROR);
    }

    @Override
    public boolean reject(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Apply> wrapper = new UpdateWrapper<Apply>().lambda();
        wrapper.set(Apply::getApplyState, ApplyStatus.REJECT.getCode());
        wrapper.set(Apply::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Apply::getId, dto.getId());
        wrapper.eq(Apply::getApplyState, ApplyStatus.WAITCONFIRM.getCode());
        if (this.update(wrapper)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.APPLY_REJECT_ERROR);
    }

    @Override
    public ApplyRealNameVo realName(Integer id) {
        Apply apply = this.getById(id);
        ApplyRealNameVo vo = new ApplyRealNameVo();
        BeanUtils.copyProperties(apply, vo);
        return vo;
    }

    @Override
    public IPage<DistributionUserVo> users(DistributionUserSearchDto dto) {
        IPage<DistributionUserVo> result = new Page<>();

        //构建查询条件
        LambdaQueryWrapper<Apply> lambda = new QueryWrapper<Apply>().lambda();
        lambda.orderByDesc(Apply::getId);
        lambda.eq(Apply::getApplyState, ApplyStatus.APPROVE.getCode());
        if (Objects.nonNull(dto.getUserId())) {
            lambda.eq(Apply::getUserId, dto.getUserId());
        }
        if (StringUtils.isNotEmpty(dto.getName())) {
            lambda.like(Apply::getName, dto.getName());
        }
        if (StringUtils.isNotEmpty(dto.getIdCard())) {
            lambda.eq(Apply::getIdCard, dto.getIdCard());
        }
        if (Objects.nonNull(dto.getDistributionIdentityId())) {
            lambda.eq(Apply::getDistributionIdentityId, dto.getDistributionIdentityId());
        }

        //查询申请信息
        IPage<Apply> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        //如果结果为空,直接返回
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //提取用户ID集合
        List<Integer> userIds = page.getRecords().stream().map(Apply::getUserId).collect(Collectors.toList());

        //查询微信用户信息
        StatisticalUserLowerByIdsInput statisticalUserLowerByIdsInput = new StatisticalUserLowerByIdsInput();
        statisticalUserLowerByIdsInput.setUserIds(userIds);
        StatisticalUserLowerRespOutput statisticalUserLowerRespOutput = wxUserService.statisticalUserLowerByIds(statisticalUserLowerByIdsInput);
        Map<Integer, StatisticalUserLowerOutput> statisticalMap = statisticalUserLowerRespOutput.getStatisticalMap();
        StatisticalUserLowerOutput statisticalUserLowerOutputEmpty = new StatisticalUserLowerOutput();

        //查询消费信息
        StatisticalOrderDto statisticalOrderDto = new StatisticalOrderDto();
        statisticalOrderDto.setUserIdList(userIds);
        StatisticalOrderRespVo respVo = orderService.statisticalOrder(statisticalOrderDto);
        Map<Integer, StatisticalOrderInfo> orderInfoMap = null == respVo.getStatisticalOrderMap()
                ? Collections.EMPTY_MAP : respVo.getStatisticalOrderMap();
        StatisticalOrderInfo statisticalOrderInfoEmpty = new StatisticalOrderInfo();

        //查询分销身份
        Set<Integer> identityIds = page.getRecords().stream()
                .map(Apply::getDistributionIdentityId).collect(Collectors.toSet());
        Collection<Identity> identities = identityService.listByIds(identityIds);
        Map<Integer, Identity> identityMap = identities.stream()
                .collect(Collectors.toMap(Identity::getId, identity -> identity));

        //查询账户信息
        LambdaQueryWrapper<Account> accountLambda = new QueryWrapper<Account>().lambda();
        accountLambda.in(Account::getUserId, userIds);
        List<Account> accounts = accountService.list(accountLambda);
        Map<Integer, Account> accountMap = accounts.stream()
                .collect(Collectors.toMap(Account::getUserId, account -> account));

        //查询可提现金额信息
        LambdaQueryWrapper<Withdraw> wrapper = new QueryWrapper<Withdraw>().lambda();
        wrapper.in(Withdraw::getUserId, userIds);
        wrapper.eq(Withdraw::getState, WithdrawStatus.WAITCONFIRM.getCode());
        wrapper.groupBy(Withdraw::getUserId);
        List<WithdrawSumAmount> withdrawSumAmounts = withdrawMapper.sumAmounts(wrapper);
        Map<Integer, WithdrawSumAmount> withdrawSumAmountMap = withdrawSumAmounts.stream()
                .collect(Collectors.toMap(WithdrawSumAmount::getUserId, withdrawSumAmount -> withdrawSumAmount));

        //关联查询结果
        List<DistributionUserVo> distributionUserVos = page.getRecords().stream().map(apply -> {
            DistributionUserVo vo = new DistributionUserVo();
            BeanUtils.copyProperties(apply, vo);
            //关联用户信息
            StatisticalUserLowerOutput statisticalUserLowerOutput =
                    statisticalMap.getOrDefault(apply.getUserId(), statisticalUserLowerOutputEmpty);
            vo.setStairFansCount(null == statisticalUserLowerOutput.getLower1Count() ? 0
                    : statisticalUserLowerOutput.getLower1Count());
            vo.setSecondLevelFansCount(null == statisticalUserLowerOutput.getLower2Count() ? 0
                    : statisticalUserLowerOutput.getLower2Count());
            vo.setInviterId(statisticalUserLowerOutput.getInviteParentId());
            vo.setQuickMark(statisticalUserLowerOutput.getQuickMark());
            //关联消费信息
            StatisticalOrderInfo statisticalOrderInfo = orderInfoMap
                    .getOrDefault(apply.getUserId(), statisticalOrderInfoEmpty);
            vo.setAddUpExpense(null == statisticalOrderInfo.getTotalAmount() ? BigDecimal.ZERO
                    : statisticalOrderInfo.getTotalAmount());
            //关联分销身份
            Identity identity = identityMap.getOrDefault(apply.getDistributionIdentityId(), new Identity());
            vo.setDistributionIdentity(identity.getName());
            //关联帐号信息
            if (accountMap.containsKey(apply.getUserId())){
                Account account = accountMap.get(apply.getUserId());
                vo.setBalance(account.getBalance());
            }
            //关联可提现金额
            if (accountMap.containsKey(apply.getUserId())){
                Account account = accountMap.get(apply.getUserId());
                vo.setBalance(account.getBalance());
                vo.setAddUpCashBack(account.getAddUpCashBack());
            } else {
                vo.setBalance(BigDecimal.ZERO);
                vo.setAddUpCashBack(BigDecimal.ZERO);
            }
            if (withdrawSumAmountMap.containsKey(apply.getUserId())){
                WithdrawSumAmount withdrawSumAmount = withdrawSumAmountMap.get(apply.getUserId());
                BigDecimal available = vo.getBalance().subtract(withdrawSumAmount.getAmount());
                vo.setBalance(available.doubleValue() > 0 ? available : BigDecimal.ZERO);
            }
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(distributionUserVos);
        return result;
    }

    @Override
    public IPage<StairFansVo> stairFans(FansUserSearchDto dto) {
        IPage<StairFansVo> result = new Page<>();

        //查询分销申请信息获取userIds
        List<Integer> userIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(dto.getName()) || StringUtils.isNotEmpty(dto.getIdCard())
                || Objects.nonNull(dto.getDistributionIdentityId())) {
            //构建查询条件
            LambdaQueryWrapper<Apply> lambda = new QueryWrapper<Apply>().lambda();
            lambda.select(Apply::getUserId);
            //获取用户信息
            if (Objects.nonNull(dto.getUserId())) {
                lambda.eq(Apply::getUserId, dto.getUserId());
            }
            lambda.eq(Apply::getInviteParentId, dto.getInviteParentId());
            if (StringUtils.isNotEmpty(dto.getName())) {
                lambda.like(Apply::getName, dto.getName());
            }
            if (StringUtils.isNotEmpty(dto.getIdCard())) {
                lambda.eq(Apply::getIdCard, dto.getIdCard());
            }
            if (Objects.nonNull(dto.getDistributionIdentityId())) {
                lambda.eq(Apply::getDistributionIdentityId, dto.getDistributionIdentityId());
            }
            //查询申请信息
            List<Apply> applies = this.list(lambda);
            //如果结果为空,直接返回
            if (CollectionUtils.isEmpty(applies)) {
                return result;
            }
            userIds = applies.stream().map(Apply::getUserId).collect(Collectors.toList());
        }

        //获取用户信息
        if (Objects.nonNull(dto.getUserId())) {
            userIds.add(dto.getUserId());
        }
        GetLowerInput getLowerInput = new GetLowerInput();
        getLowerInput.setUserId(dto.getInviteParentId());
        getLowerInput.setLowerIds(userIds);
        getLowerInput.setLowerTelnum(dto.getPhone());
        getLowerInput.setPageSize(dto.getPageSize());
        getLowerInput.setCurrentPage(dto.getCurrentPage());
        Page<UserOutput> page = wxUserService.getLower1List(getLowerInput);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //查询申请信息
        List<Integer> ids = page.getRecords().stream().map(UserOutput::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Apply> lambda = new QueryWrapper<Apply>().lambda();
        lambda.in(Apply::getUserId, ids);
        List<Apply> applies = list(lambda);
        Map<Integer, Apply> applyMap = applies.stream().collect(Collectors.toMap(Apply::getUserId, apply -> apply));

        //查询消费信息
        StatisticalOrderDto statisticalOrderDto = new StatisticalOrderDto();
        statisticalOrderDto.setUserIdList(ids);
        StatisticalOrderRespVo respVo = orderService.statisticalOrder(statisticalOrderDto);
        Map<Integer, StatisticalOrderInfo> orderInfoMap = null == respVo.getStatisticalOrderMap()
                ? Collections.EMPTY_MAP : respVo.getStatisticalOrderMap();
        StatisticalOrderInfo statisticalOrderInfoEmpty = new StatisticalOrderInfo();

        //查询分销身份
        Set<Integer> identityIds = applies.stream()
                .map(Apply::getDistributionIdentityId).collect(Collectors.toSet());
        Map<Integer, Identity> identityMap = getIdentityMap(identityIds);

        //关联查询结果
        List<StairFansVo> vos = page.getRecords().stream().map(userOutput -> {
            StairFansVo vo = new StairFansVo();
            //关联用户信息
            vo.setUserId(userOutput.getId());
            vo.setNickname(userOutput.getNickname());
            vo.setPhone(userOutput.getTelnum());
            vo.setStairFansCount(userOutput.getLower1Count());
            //关联分销申请信息
            if (applyMap.containsKey(userOutput.getId())) {
                Apply apply = applyMap.get(userOutput.getId());
                BeanUtils.copyProperties(apply, vo);
                //关联分销身份信息
                vo.setDistributionIdentity(identityMap.get(apply.getDistributionIdentityId()).getName());
            }
            //关联消费信息
            StatisticalOrderInfo statisticalOrderInfo = orderInfoMap
                    .getOrDefault(userOutput.getId(), statisticalOrderInfoEmpty);
            vo.setAddUpExpense(null == statisticalOrderInfo.getTotalAmount() ? BigDecimal.ZERO
                    : statisticalOrderInfo.getTotalAmount());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(vos);
        return result;
    }

    @Override
    public IPage<SecondLevelFansVo> secondLevelFans(FansUserSearchDto dto) {
        IPage<SecondLevelFansVo> result = new Page<>();

        //查询分销申请信息获取userIds
        List<Integer> userIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(dto.getName()) || StringUtils.isNotEmpty(dto.getIdCard())
                || Objects.nonNull(dto.getDistributionIdentityId())) {
            //构建查询条件
            LambdaQueryWrapper<Apply> lambda = new QueryWrapper<Apply>().lambda();
            lambda.select(Apply::getUserId);
            lambda.inSql(Apply::getInviteParentId,
                    "select user_id from tb_distribution_identity where invite_parent_id = '"
                            + dto.getInviteParentId() + "'");
            //获取用户信息
            if (Objects.nonNull(dto.getUserId())) {
                lambda.eq(Apply::getUserId, dto.getUserId());
            }
            lambda.eq(Apply::getInviteParentId, dto.getInviteParentId());
            if (StringUtils.isNotEmpty(dto.getName())) {
                lambda.like(Apply::getName, dto.getName());
            }
            if (StringUtils.isNotEmpty(dto.getIdCard())) {
                lambda.eq(Apply::getIdCard, dto.getIdCard());
            }
            if (Objects.nonNull(dto.getDistributionIdentityId())) {
                lambda.eq(Apply::getDistributionIdentityId, dto.getDistributionIdentityId());
            }
            //查询申请信息
            List<Apply> applies = this.list(lambda);
            //如果结果为空,直接返回
            if (CollectionUtils.isEmpty(applies)) {
                return result;
            }
            userIds = applies.stream().map(Apply::getUserId).collect(Collectors.toList());
        }

        //获取用户信息
        if (Objects.nonNull(dto.getUserId())) {
            userIds.add(dto.getUserId());
        }
        GetLowerInput getLowerInput = new GetLowerInput();
        getLowerInput.setUserId(dto.getInviteParentId());
        getLowerInput.setLowerIds(userIds);
        getLowerInput.setLowerTelnum(dto.getPhone());
        getLowerInput.setPageSize(dto.getPageSize());
        getLowerInput.setCurrentPage(dto.getCurrentPage());
        Page<UserOutput> page = wxUserService.getLower2List(getLowerInput);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //查询申请信息
        List<Integer> ids = page.getRecords().stream().map(UserOutput::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Apply> lambda = new QueryWrapper<Apply>().lambda();
        lambda.in(Apply::getUserId, ids);
        List<Apply> applies = list(lambda);
        Map<Integer, Apply> applyMap = applies.stream().collect(Collectors.toMap(Apply::getUserId, apply -> apply));

        //查询消费信息
        StatisticalOrderDto statisticalOrderDto = new StatisticalOrderDto();
        statisticalOrderDto.setUserIdList(ids);
        StatisticalOrderRespVo respVo = orderService.statisticalOrder(statisticalOrderDto);
        Map<Integer, StatisticalOrderInfo> orderInfoMap = null == respVo.getStatisticalOrderMap()
                ? Collections.EMPTY_MAP : respVo.getStatisticalOrderMap();
        StatisticalOrderInfo statisticalOrderInfoEmpty = new StatisticalOrderInfo();

        //查询分销身份
        Set<Integer> identityIds = applies.stream()
                .map(Apply::getDistributionIdentityId).collect(Collectors.toSet());
        Map<Integer, Identity> identityMap = getIdentityMap(identityIds);

        //关联查询结果
        List<SecondLevelFansVo> vos = page.getRecords().stream().map(userOutput -> {
            SecondLevelFansVo vo = new SecondLevelFansVo();
            //关联用户信息
            vo.setUserId(userOutput.getId());
            vo.setNickname(userOutput.getNickname());
            vo.setPhone(userOutput.getTelnum());
            //关联分销申请信息
            if (applyMap.containsKey(userOutput.getId())) {
                Apply apply = applyMap.get(userOutput.getId());
                BeanUtils.copyProperties(apply, vo);
                //关联分销身份信息
                vo.setDistributionIdentity(identityMap.get(apply.getDistributionIdentityId()).getName());
            }
            //关联消费信息
            StatisticalOrderInfo statisticalOrderInfo = orderInfoMap
                    .getOrDefault(userOutput.getId(), statisticalOrderInfoEmpty);
            vo.setAddUpExpense(null == statisticalOrderInfo.getTotalAmount() ? BigDecimal.ZERO
                    : statisticalOrderInfo.getTotalAmount());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(vos);
        return result;
    }

    private Map<Integer, Identity> getIdentityMap(Set<Integer> identityIds) {
        if (CollectionUtils.isEmpty(identityIds)) {
            return Collections.EMPTY_MAP;
        }
        Collection<Identity> identities = identityService.listByIds(identityIds);
        return identities.stream()
                .collect(Collectors.toMap(Identity::getId, identity -> identity));
    }

}