package com.sep.distribution.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.sep.distribution.model.Withdraw;
import com.sep.distribution.model.WithdrawSumAmount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 提现表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface WithdrawMapper extends BaseMapper<Withdraw> {

    /**
     * 统计待审核提现金额
     *
     * @param wrapper 查询条件
     * @return 收益
     */
    BigDecimal sumAmount(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 分组统计待审核提现金额
     *
     * @param wrapper 查询条件
     * @return 收益
     */
    List<WithdrawSumAmount> sumAmounts(@Param(Constants.WRAPPER) Wrapper wrapper);

}