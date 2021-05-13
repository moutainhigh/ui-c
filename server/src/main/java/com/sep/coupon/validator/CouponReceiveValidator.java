package com.sep.coupon.validator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sep.common.exceptions.SepCustomException;
import com.sep.coupon.dto.ReceiveDto;
import com.sep.coupon.enums.BizErrorCode;
import com.sep.coupon.model.ReceiveRecord;
import com.sep.coupon.repository.ReceiveRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CouponReceiveValidator {

    @Resource
    private ReceiveRecordMapper receiveRecordMapper;

    public void validator(ReceiveDto dto) {
        LambdaQueryWrapper<ReceiveRecord> lambda = new QueryWrapper<ReceiveRecord>().lambda();
        lambda.eq(ReceiveRecord::getCouponId, dto.getCouponId());
        lambda.eq(ReceiveRecord::getUserId, dto.getUserId());
        Integer count = receiveRecordMapper.selectCount(lambda);
        if (count > 0) {
            throw new SepCustomException(BizErrorCode.COUPON_RECEIVED);
        }
    }

}