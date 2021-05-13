package com.sep.content.service;

import com.sep.content.dto.ActivityEnterWxpayDto;
import com.sep.content.model.ActivityEnterWxpay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户付费项目表  服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-16
 */
public interface ActivityEnterWxpayService extends IService<ActivityEnterWxpay> {

    Integer pageSearchInfo(String orderNo);

    Integer add(ActivityEnterWxpayDto activityEnterWxpayDto);
}
