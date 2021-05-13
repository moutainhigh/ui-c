package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.CashBackPageSearchDto;
import com.sep.distribution.dto.CashbackDto;
import com.sep.distribution.model.CashBack;
import com.sep.distribution.vo.background.CashBackDetailsVo;

import java.math.BigDecimal;

/**
 * <p>
 * 返现表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface CashBackService extends IService<CashBack> {

    /**
     * 今日收益
     *
     * @param userId 用户ID
     * @return 今日收益
     */
    BigDecimal todayEarnings(Integer userId);

    /**
     * 今日返现订单数量统计
     *
     * @param userId 用户ID
     * @return 今日返现订单数量
     */
    Integer todayOrder(Integer userId);

    /**
     * 累计返现订单数量统计
     *
     * @param userId 用户ID
     * @return 累计返现订单数量
     */
    Integer addUpOrder(Integer userId);

    /**
     * 返现
     *
     * @param dto 请求参数
     * @return 返现是否成功
     */
    Boolean cashback(CashbackDto dto);

    /**
     * 返现分页查询
     * @param dto 请求参数
     * @return 返现详情
     */
    IPage<CashBackDetailsVo> pageSearch(CashBackPageSearchDto dto);

}