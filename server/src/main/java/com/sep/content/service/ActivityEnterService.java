package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddEnterDto;
import com.sep.content.dto.SearchEnterDto;
import com.sep.content.model.ActivityEnter;
import com.sep.content.vo.ActivityEnterVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-09
 */
public interface ActivityEnterService extends IService<ActivityEnter> {


    /**
     * 小程序接口
     *  添加活动报名
     *  添加报名成功 减活动名额
     *
     * */
    Integer enter(AddEnterDto addEnterDto);

    /**
     *  管理后台接口
     *  查询活动报名人列表
     * */
    IPage<ActivityEnterVo> searchActivityEnter(SearchEnterDto searchEnterDto);

    List<Integer>  getActivityIds(Integer userId);


}
