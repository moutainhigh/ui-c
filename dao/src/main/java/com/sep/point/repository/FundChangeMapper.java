package com.sep.point.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.sep.point.model.FundChange;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 积分变动表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
public interface FundChangeMapper extends BaseMapper<FundChange> {

    /**
     * 统计收益
     *
     * @param wrapper 查询条件
     * @return 收益
     */
    int sumAmount(@Param(Constants.WRAPPER) Wrapper wrapper);

}