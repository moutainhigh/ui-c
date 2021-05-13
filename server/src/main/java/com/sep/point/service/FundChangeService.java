package com.sep.point.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.point.dto.PageSearchFundChangeDto;
import com.sep.point.dto.SearchFundChangeDto;
import com.sep.point.model.FundChange;
import com.sep.point.vo.FundChangeVo;

import java.time.LocalDateTime;

/**
 * <p>
 * 积分变动表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
public interface FundChangeService extends IService<FundChange> {

    /**
     * 今日增加积分次数
     *
     * @param userId         用户ID
     * @param fundChangeType 变动类型
     * @return 增加积分次数
     */
    int toDayIncreaseCount(Integer userId, int fundChangeType);

    /**
     * 今日增加积分
     *
     * @param userId 用户ID
     * @return 增加积分
     */
    int toDayEarnings(Integer userId);

    /**
     * 本周增加积分
     *
     * @param userId 用户ID
     * @return 增加积分
     */
    int weekendEarnings(Integer userId);

    /**
     * 本月增加积分
     *
     * @param userId 用户ID
     * @return 增加积分
     */
    int currentMonthEarnings(Integer userId);


    /**
     * 后端分页查询
     *
     * @param dto 请求参数
     * @return 变动记录
     */
    IPage<FundChangeVo> pageSearch(PageSearchFundChangeDto dto);

    /**
     * 小程序端分页查询
     *
     * @param dto 请求参数
     * @return 变动记录
     */
    IPage<FundChangeVo> pageSearch(SearchFundChangeDto dto);

    /**
     * 判断订单号是否存在
     *
     * @param orderNo 订单号
     * @return 存在:true
     */
    boolean isExistByOrderNo(String orderNo);

    /**
     * 收益查询
     *
     * @param userId 用户ID
     * @param start  查询开始时间
     * @param end    查询结束时间
     * @return 收益
     */
    int getPointByDateTimeRange(Integer userId, LocalDateTime start, LocalDateTime end);

}