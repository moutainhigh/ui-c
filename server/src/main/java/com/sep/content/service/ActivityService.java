package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddActivityDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchActivityDto;
import com.sep.content.model.Activity;
import com.sep.content.vo.ActivityVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 管理后台接口
     * 添加活动
     * 根据活动时间判断出活动状态
     */
    Integer addActivity(AddActivityDto addActivityDto);

    /**
     * 通用接口
     * 获取活动详情
     */
    ActivityVo getActivity(IdDto idDto, Boolean isReturnSku);

    /**
     * 管理后台接口
     * 删除活动
     */
    Integer delActivity(IdDto idDto);

    /**
     * 通用接口
     * 查询活动列表
     */
    IPage<ActivityVo> searchActivity(SearchActivityDto searchActivityDto);

    /**
     * 小程序接口
     * 查询首页接口
     */
    List<ActivityVo> getHomeActivityVo();

    /**
     * 内部接口
     * 减活动名额
     * 调用一次 活动名额减一
     */
    Integer subtractNum(IdDto idDto);

    /**
     * 内部接口
     * 添加活动转发数
     * 调用一次 活动转发数加一
     */
    Integer plusRetransmission(IdDto idDto);

    /**
     * 内部接口
     * 添加活动点赞数
     * 调用一次 点赞数量加一
     */
    Integer plusPraise(IdDto idDto);


    /**
     * 内部接口
     * 减活动点赞数量
     * 调用一次 点赞数量加一
     */
    Integer subtracPraise(IdDto idDto);

    /**
     * 内部接口
     *
     *根据活动标题返回活动Id
     */
    List<Integer> getIds(String title);

    /**
     * 定时任务
     * 活动开始时间 > 当前时间 =未开始
     * 活动开始时间 < 当前时间 =进行中
     * 活动结束时间 < 当前时间 =已完成
     */
    void updateActivityStatus();

    Boolean isActivityOneLine(Integer id);

    Integer getCurrentNum(Integer id);


}
