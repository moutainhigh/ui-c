package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.AccountAddDto;
import com.sep.distribution.dto.DistributionOrderDto;
import com.sep.distribution.dto.FansSearchDto;
import com.sep.distribution.model.Account;
import com.sep.distribution.vo.xcx.AccountDetailsVo;
import com.sep.distribution.vo.xcx.DistributionOrderVo;
import com.sep.distribution.vo.xcx.FansVo;

import java.math.BigDecimal;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface AccountService extends IService<Account> {

    /**
     * 账号详情
     *
     * @param userId 用户id
     * @return 账号详情
     */
    AccountDetailsVo detail(Integer userId);

    /**
     * 查询余额
     *
     * @param userId 用户id
     * @return 余额
     */
    BigDecimal balance(Integer userId);

    /**
     * 增加金额
     *
     * @param type   变动原因
     * @param amount 变动金额
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean addition(Integer type, BigDecimal amount, Integer userId);

    /**
     * 减少金额
     *
     * @param type   变动原因
     * @param amount 变动金额
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean subtraction(Integer type, BigDecimal amount, Integer userId);

    /**
     * 一级粉丝查询
     *
     * @param dto 请求参数
     * @return 一级粉丝
     */
    IPage<FansVo> stairFans(FansSearchDto dto);

    /**
     * 二级粉丝
     *
     * @param dto 请求参数
     * @return 二级粉丝
     */
    IPage<FansVo> secondLevelFans(FansSearchDto dto);

    /**
     * 创建帐号
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean add(AccountAddDto dto);

    /**
     * 分销订单分页查询
     *
     * @param dto 请求参数
     * @return 详情
     */
    IPage<DistributionOrderVo> distributionOrder(DistributionOrderDto dto);

}