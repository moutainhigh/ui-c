package com.sep.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddPraiseDto;
import com.sep.content.model.Praise;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface PraiseService extends IService<Praise> {


    /**
     * 小程序端接口
     * 添加点赞
     * 添加成功后根据类型对应更新点赞数量
     * */
    Integer addPraise(AddPraiseDto addPraiseDto);

    /**
     * 小程序端接口
     * 取消点赞
     * 取消后成功后根据类型对应更新点赞数量
     * */
    Integer delPraise(AddPraiseDto addPraiseDto);

    /**
     * 小程序接口
     * */
    Integer isPraise(AddPraiseDto addPraiseDto);





}
