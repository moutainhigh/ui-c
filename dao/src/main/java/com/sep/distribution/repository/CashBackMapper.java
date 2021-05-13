package com.sep.distribution.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.sep.distribution.model.CashBack;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 返现表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface CashBackMapper extends BaseMapper<CashBack> {

    /**
     * 统计收益
     *
     * @param wrapper 查询条件
     * @return 收益
     */
    BigDecimal sumAmount(@Param(Constants.WRAPPER) Wrapper wrapper);

}