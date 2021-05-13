package com.sep.content.service.impl;

import com.sep.content.dto.ActivityEnterWxpayDto;
import com.sep.content.model.ActivityEnterWxpay;
import com.sep.content.repository.ActivityEnterWxpayMapper;
import com.sep.content.service.ActivityEnterWxpayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户付费项目表  服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-16
 */
@Service
public class ActivityEnterWxpayServiceImpl extends ServiceImpl<ActivityEnterWxpayMapper, ActivityEnterWxpay> implements ActivityEnterWxpayService {

    @Override
    public Integer pageSearchInfo(String orderNo) {
        return lambdaQuery().eq(ActivityEnterWxpay::getActivityNo,orderNo).count();
    }

    @Override
    public Integer add(ActivityEnterWxpayDto activityEnterWxpayDto) {
        ActivityEnterWxpay activityEnterWxpay=new ActivityEnterWxpay();
        BeanUtils.copyProperties(activityEnterWxpayDto,activityEnterWxpay);
        activityEnterWxpay.setCreatedTime(LocalDateTime.now());
        return activityEnterWxpay.insert()?1:0;
    }
}
