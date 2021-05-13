package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.distribution.dto.CashBackPageSearchDto;
import com.sep.distribution.dto.CashbackDto;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.enums.CashBackWay;
import com.sep.distribution.enums.DistributionRank;
import com.sep.distribution.enums.FundChangeType;
import com.sep.distribution.model.CashBack;
import com.sep.distribution.model.Identity;
import com.sep.distribution.repository.CashBackMapper;
import com.sep.distribution.service.*;
import com.sep.distribution.vo.background.CashBackDetailsVo;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 返现表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class CashBackServiceImpl extends ServiceImpl<CashBackMapper, CashBack> implements CashBackService {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private CashBackMapper cashBackMapper;
    @Resource
    private ApplyService applyService;
    @Resource
    private IdentityService identityService;
    @Resource
    private AccountService accountService;

    @Override
    public BigDecimal todayEarnings(Integer userId) {
        LambdaQueryWrapper<CashBack> query = new QueryWrapper<CashBack>().lambda();
        query.eq(CashBack::getBeneficiary, userId);
        query.ge(CashBack::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        query.le(CashBack::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        return cashBackMapper.sumAmount(query);
    }

    @Override
    public Integer todayOrder(Integer userId) {
        LambdaQueryWrapper<CashBack> query = new QueryWrapper<CashBack>().lambda();
        query.eq(CashBack::getBeneficiary, userId);
        query.ge(CashBack::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        query.le(CashBack::getCreateTime, LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        return this.count(query);
    }

    @Override
    public Integer addUpOrder(Integer userId) {
        LambdaQueryWrapper<CashBack> query = new QueryWrapper<CashBack>().lambda();
        query.eq(CashBack::getBeneficiary, userId);
        return this.count(query);
    }

    @Transactional
    @Override
    public Boolean cashback(CashbackDto dto) {
        //判断是否已经执行过分销，如果执行过分销则返回true，保证幂等性
        LambdaQueryWrapper<CashBack> wrapper = new QueryWrapper<CashBack>().lambda();
        wrapper.eq(CashBack::getOrderId, dto.getOrderId());
        int count = this.count(wrapper);
        if (count > 0) {
            return true;
        }

        //执行返现
        //获取一级分销用户
        GetUserInput getUserInput = new GetUserInput();
        getUserInput.setUserId(dto.getConsumer());

        UserOutput userOutput = wxUserService.getParentUser(getUserInput);
        if (Objects.isNull(userOutput)) {
            return true;
        }
        Integer stairUser = userOutput.getId();
        CashBack stair = cashback(dto, stairUser, DistributionRank.STAIR);
        //如果一级分销为空，则表示不需要进行分销
        if (Objects.isNull(stair)) {
            return true;
        }
        //获取二级分销用户
        getUserInput.setUserId(stairUser);
        userOutput = wxUserService.getParentUser(getUserInput);
        Integer secondLevelUser = null;
        if (Objects.nonNull(userOutput)) {
            secondLevelUser = userOutput.getId();
        }
        CashBack secondLevel = cashback(dto, secondLevelUser, DistributionRank.SECOND_LEVEL);
        //入库
        boolean bool = save(stair, FundChangeType.STAIR_DISTRIBUTION.getCode(), stairUser);
        if (Objects.nonNull(secondLevel)) {
            bool = save(secondLevel, FundChangeType.SECOND_LEVEL_DISTRIBUTION.getCode(), secondLevelUser);
        }
        if (!bool) {
            throw new SepCustomException(BizErrorCode.CASHBACK_ERROR);
        }
        return true;
    }

    @Override
    public IPage<CashBackDetailsVo> pageSearch(CashBackPageSearchDto dto) {
        IPage<CashBackDetailsVo> result = new Page<>();
        //获取受益人
        Integer beneficiary = null;
        if (Objects.nonNull(dto.getBeneficiary()) || StringUtils.isNotEmpty(dto.getBeneficiaryPhone())) {
            GetUserInput getUserInput = new GetUserInput();
            getUserInput.setUserId(dto.getBeneficiary());
            getUserInput.setTelnum(dto.getBeneficiaryPhone());
            UserOutput userOutput = wxUserService.getUser(getUserInput);
            if (Objects.isNull(userOutput) || Objects.isNull(userOutput.getId())) {
                return result;
            }
            beneficiary = userOutput.getId();
        }
        //获取消费者
        Integer consumer = null;
        if (Objects.nonNull(dto.getConsumer()) || StringUtils.isNotEmpty((dto.getConsumerPhone()))) {
            GetUserInput getUserInput = new GetUserInput();
            getUserInput.setUserId(dto.getConsumer());
            getUserInput.setTelnum(dto.getConsumerPhone());
            UserOutput userOutput = wxUserService.getUser(getUserInput);
            if (Objects.isNull(userOutput) || Objects.isNull(userOutput.getId())) {
                return result;
            }
            consumer = userOutput.getId();
        }

        //构建查询条件
        LambdaQueryWrapper<CashBack> query = new QueryWrapper<CashBack>().lambda();
        query.orderByDesc(CashBack::getId);
        if (Objects.nonNull(beneficiary)) {
            query.eq(CashBack::getBeneficiary, beneficiary);
        }
        if (Objects.nonNull(consumer)) {
            query.eq(CashBack::getConsumer, consumer);
        }
        if (Objects.nonNull(dto.getRank())) {
            query.eq(CashBack::getRank, dto.getRank());
        }
        if (StringUtils.isNotEmpty(dto.getOrderNo())) {
            query.eq(CashBack::getOrderNo, dto.getOrderNo());
        }
        //执行查询
        IPage<CashBack> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), query);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }
        //获取用户id集合
        List<Integer> beneficiarys = page.getRecords().stream().map(CashBack::getBeneficiary).collect(Collectors.toList());
        List<Integer> consumers = page.getRecords().stream().map(CashBack::getConsumer).collect(Collectors.toList());
        List<Integer> userIds = Lists.newArrayList();
        userIds.addAll(beneficiarys);
        userIds.addAll(consumers);
        //获取用户信息
        GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
        getUserByIdsInput.setUserIds(userIds);
        List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
        Map<Integer, UserOutput> userMap = userOutputs.stream().
                collect(Collectors.toMap(UserOutput::getId, userOutput -> userOutput));
        UserOutput emptyUser = new UserOutput();
        //转换查询结果
        List<CashBackDetailsVo> vos = page.getRecords().stream().map(cashBack -> {
            CashBackDetailsVo vo = new CashBackDetailsVo();
            BeanUtils.copyProperties(cashBack, vo);
            vo.setBeneficiaryPhone(userMap.getOrDefault(cashBack.getBeneficiary(), emptyUser).getTelnum());
            vo.setConsumerPhone(userMap.getOrDefault(cashBack.getConsumer(), emptyUser).getTelnum());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(vos);
        return result;
    }

    /**
     * 保存返现
     *
     * @param cashBack 返现记录
     * @param type     变动原因
     * @param userId   用户id
     * @return 是否成功
     */
    private boolean save(CashBack cashBack, Integer type, Integer userId) {
        return this.save(cashBack)
                && accountService.addition(type, cashBack.getAmount().multiply(new BigDecimal("1")).setScale(2, BigDecimal.ROUND_DOWN), userId);
    }

    /**
     * 返现
     *
     * @param dto         返现请求参数
     * @param beneficiary 收益人
     * @param rank        分销返现等级
     * @return 返现对象
     */
    private CashBack cashback(CashbackDto dto, Integer beneficiary, DistributionRank rank) {
        Optional<Identity> optional = getIdentity(beneficiary);
        if (optional.isPresent()) {
            Identity identity = optional.get();
            Integer interest = identity.getStairInterest();
            if (DistributionRank.SECOND_LEVEL == rank) {
                interest = identity.getSecondLevelInterest();
            }
            BigDecimal amount = getAmount(identity.getCashBackWay(), interest, dto.getMonetary());
            CashBack cashBack = convert(dto);
            cashBack.setCashBackWay(identity.getCashBackWay());
            cashBack.setInterest(interest);
            cashBack.setRank(rank.getCode());
            cashBack.setAmount(amount);
            cashBack.setBeneficiary(beneficiary);
            return cashBack;
        }
        return null;
    }

    /**
     * 获取返现身份和规则
     *
     * @param beneficiary 受益人用户id
     * @return 分销身份
     */
    private Optional<Identity> getIdentity(Integer beneficiary) {
        //如果获取不到上级，则表示不需要分销
        if (Objects.isNull(beneficiary)) {
            return Optional.empty();
        }
        Optional<Integer> identityId = applyService.getIdentityByUserId(beneficiary);
        //如果获取不到上级，则表示不需要分销
        if (identityId.isPresent()) {
            return identityService.getEnabledById(identityId.get());
        }
        return Optional.empty();
    }

    /**
     * 返现请求对象转换
     *
     * @param dto 对象转换
     * @return 返现对象
     */
    private CashBack convert(CashbackDto dto) {
        CashBack cashBack = new CashBack();
        BeanUtils.copyProperties(dto, cashBack);
        cashBack.setCreateTime(LocalDateTime.now());
        cashBack.setUpdateTime(LocalDateTime.now());
        cashBack.setCreateUid("sku");
        cashBack.setUpdateUid("sku");
        return cashBack;
    }

    /**
     * 计算返现金额
     *
     * @param cashBackWay 返现计算方式
     * @param interest    利息
     * @param monetary    消费金额
     * @return 返现金额
     */
    private BigDecimal getAmount(Integer cashBackWay, Integer interest, BigDecimal monetary) {
        return CashBackWay.FIXATION.getCode() == cashBackWay ?
                BigDecimal.valueOf(interest) :
                BigDecimal.valueOf(interest).divide(BigDecimal.valueOf(100),
                        2, BigDecimal.ROUND_HALF_UP).multiply(monetary);
    }

}