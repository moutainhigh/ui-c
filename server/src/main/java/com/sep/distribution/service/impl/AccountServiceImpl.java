package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.utils.DateUtils;
import com.sep.distribution.dto.AccountAddDto;
import com.sep.distribution.dto.DistributionOrderDto;
import com.sep.distribution.dto.FansSearchDto;
import com.sep.distribution.model.Account;
import com.sep.distribution.repository.AccountMapper;
import com.sep.distribution.service.AccountService;
import com.sep.distribution.service.FundChangeService;
import com.sep.distribution.service.WithdrawService;
import com.sep.distribution.vo.xcx.*;
import com.sep.sku.bean.StatisticalOrderInfo;
import com.sep.sku.dto.ServerBatchSearchOrderDto;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.service.OrderService;
import com.sep.sku.vo.ServerSearchOrderRespVo;
import com.sep.sku.vo.ServerSearchOrderSkuPropertyRespVo;
import com.sep.sku.vo.ServerSearchOrderSkuRespVo;
import com.sep.sku.vo.StatisticalOrderRespVo;
import com.sep.user.input.GetLowerInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.output.LowerIdOutput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    private FundChangeService fundChangeService;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private OrderService orderService;
    @Resource
    private WithdrawService withdrawService;

    @Override
    public AccountDetailsVo detail(Integer userId) {
        LambdaQueryWrapper<Account> query = new QueryWrapper<Account>().lambda();
        query.eq(Account::getUserId, userId);
        Account account = this.getOne(query);
        if (null == account) {
            AccountDetailsVo vo = new AccountDetailsVo();
            vo.setAddUpCashBack(BigDecimal.ZERO);
            vo.setBalance(BigDecimal.ZERO);
            return vo;
        }
        AccountDetailsVo vo = new AccountDetailsVo();
        BeanUtils.copyProperties(account, vo);
        vo.setBalance(withdrawService.available(userId));
        vo.setTotalWithdraw(withdrawService.totalWithdraw(userId));
        return vo;
    }

    @Override
    public BigDecimal balance(Integer userId) {
        LambdaQueryWrapper<Account> query = new QueryWrapper<Account>().lambda();
        query.eq(Account::getUserId, userId);
        Account account = this.getOne(query);
        if (null == account) {
            return BigDecimal.ZERO;
        }
        return account.getBalance();
    }

    @Override
    public boolean addition(Integer type, BigDecimal amount, Integer userId) {
        BigDecimal balance = balance(userId);
        LambdaUpdateWrapper<Account> update = new LambdaUpdateWrapper<>();
        update.setSql("add_up_cash_back = add_up_cash_back + " + amount);
        update.setSql("balance = balance + " + amount);
        update.eq(Account::getUserId, userId);
        return this.update(update) &&
                fundChangeService.add(type, balance, amount, userId);
    }

    @Override
    public boolean subtraction(Integer type, BigDecimal amount, Integer userId) {
        BigDecimal balance = balance(userId);
        LambdaUpdateWrapper<Account> update = new LambdaUpdateWrapper<>();
        // update.setSql("add_up_cash_back = add_up_cash_back - " + amount);
        update.setSql("balance = balance - " + amount);
        update.eq(Account::getUserId, userId);
        update.ge(Account::getBalance, 0);
        return this.update(update) &&
                fundChangeService.add(type, balance, amount.negate(), userId);
    }

    public IPage<FansVo> stairFans(FansSearchDto dto) {
        IPage<FansVo> result = new Page<>();
        GetLowerInput input = new GetLowerInput();
        BeanUtils.copyProperties(dto, input);
        IPage<UserOutput> userOutputs = wxUserService.getLower1List(input);
        BeanUtils.copyProperties(userOutputs, result);
        result.setRecords(fansVoConvertor(userOutputs.getRecords()));
        return result;
    }

    public IPage<FansVo> secondLevelFans(FansSearchDto dto) {
        IPage<FansVo> result = new Page<>();
        GetLowerInput input = new GetLowerInput();
        BeanUtils.copyProperties(dto, input);
        IPage<UserOutput> userOutputs = wxUserService.getLower2List(input);
        BeanUtils.copyProperties(userOutputs, result);
        result.setRecords(fansVoConvertor(userOutputs.getRecords()));
        return result;
    }

    @Override
    public boolean add(AccountAddDto dto) {
        Account account = new Account();
        account.setCreateTime(LocalDateTime.now());
        account.setCreateUid(dto.getCreateUid());
        account.setUpdateTime(LocalDateTime.now());
        account.setUpdateUid(dto.getCreateUid());
        account.setUserId(dto.getUserId());
        account.setBalance(BigDecimal.ZERO);
        account.setAddUpCashBack(BigDecimal.ZERO);
        return this.save(account);
    }

    @Override
    public IPage<DistributionOrderVo> distributionOrder(DistributionOrderDto dto) {
        IPage<DistributionOrderVo> result = new Page<>();

        //获取粉丝ID集合
        GetUserInput input = new GetUserInput();
        input.setUserId(dto.getUserId());
        List<LowerIdOutput> lowers = wxUserService.getLowerIds(input);
        Map<Integer, Integer> lowersMap = lowers.stream().collect(Collectors.toMap(LowerIdOutput::getId, LowerIdOutput::getFansRank));
        List<Integer> lowerIds = lowers.stream().map(LowerIdOutput::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(lowerIds)) {
            return result;
        }

        //查询订单信息
        ServerBatchSearchOrderDto searchOrderDto = new ServerBatchSearchOrderDto();
        BeanUtils.copyProperties(dto, searchOrderDto);
        searchOrderDto.setUserIdList(lowerIds);
        if (Objects.nonNull(dto.getStartDate())) {
            searchOrderDto.setCreateTimeStart(DateUtils.format(LocalDateTime.of(dto.getStartDate(), LocalTime.MIN)));
        }
        if (Objects.nonNull(dto.getEndDate())) {
            searchOrderDto.setCreateTimeEnd(DateUtils.format(LocalDateTime.of(dto.getEndDate(), LocalTime.MAX)));
        }
        IPage<ServerSearchOrderRespVo> page = orderService.serverPageSearchSkuOrderInfo(searchOrderDto);

        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //结果转换
        List<DistributionOrderVo> collect = page.getRecords().stream().map(order -> {
            DistributionOrderVo vo = new DistributionOrderVo();
            BeanUtils.copyProperties(order, vo);
            vo.setSkus(orderSkuConvert(order.getOrderSkuList()));
            vo.setFansRank(lowersMap.get(order.getUserId()));
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    private List<DistributionOrderSkuVo> orderSkuConvert(List<ServerSearchOrderSkuRespVo> orderSkuList) {
        if (CollectionUtils.isEmpty(orderSkuList)) {
            return Collections.emptyList();
        }
        return orderSkuList.stream().map(sku -> {
            DistributionOrderSkuVo skuVo = new DistributionOrderSkuVo();
            BeanUtils.copyProperties(sku, skuVo);
            skuVo.setPropertyValueInfoList(orderSkuPropertyVoConvert(sku.getPropertyValueInfoList()));
            return skuVo;
        }).collect(Collectors.toList());
    }

    private List<OrderSkuPropertyRespVo> orderSkuPropertyVoConvert(List<ServerSearchOrderSkuPropertyRespVo> respVos) {
        if (CollectionUtils.isEmpty(respVos)) {
            return Collections.emptyList();
        }
        return respVos.stream()
                .map(propertyValueInfo -> {
                    OrderSkuPropertyRespVo skuPropertyVo = new OrderSkuPropertyRespVo();
                    BeanUtils.copyProperties(propertyValueInfo, skuPropertyVo);
                    return skuPropertyVo;
                }).collect(Collectors.toList());
    }

    /**
     * 粉丝转换器
     *
     * @param userOutputs 用户集合
     * @return 粉丝集合
     */
    private List<FansVo> fansVoConvertor(List<UserOutput> userOutputs) {
        if (!CollectionUtils.isEmpty(userOutputs)) {
            List<Integer> userIds = userOutputs.stream().map(UserOutput::getId).collect(Collectors.toList());
            StatisticalOrderDto orderDto = new StatisticalOrderDto();
            orderDto.setUserIdList(userIds);
            StatisticalOrderRespVo respVo = orderService.statisticalOrder(orderDto);
            Map<Integer, StatisticalOrderInfo> orderInfoMap = respVo.getStatisticalOrderMap();
            return userOutputs.stream().map(userOutput -> {
                FansVo vo = new FansVo();
                BeanUtils.copyProperties(userOutput, vo);
                if (!CollectionUtils.isEmpty(orderInfoMap)
                        && orderInfoMap.containsKey(userOutput.getId())) {
                    StatisticalOrderInfo orderInfo = orderInfoMap.get(userOutput.getId());
                    vo.setMonetary(null == orderInfo.getTotalAmount() ? BigDecimal.ZERO : orderInfo.getTotalAmount());
                    vo.setOrderCount(orderInfo.getTotalNum());
                } else {
                    vo.setMonetary(BigDecimal.ZERO);
                    vo.setOrderCount(0);
                }
                return vo;
            }).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

}