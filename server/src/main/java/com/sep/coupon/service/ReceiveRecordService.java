package com.sep.coupon.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.coupon.dto.MyCanUseCouponSearchDto;
import com.sep.coupon.dto.MyCouponSearchDto;
import com.sep.coupon.dto.ReceiveRecordPageSearchDto;
import com.sep.coupon.dto.UseRecordPageSearchDto;
import com.sep.coupon.model.ReceiveRecord;
import com.sep.coupon.vo.MyCanUseCouponVo;
import com.sep.coupon.vo.MyCouponVo;
import com.sep.coupon.vo.ReceiveRecordVo;
import com.sep.coupon.vo.UseRecordVo;

import java.util.List;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
public interface ReceiveRecordService extends IService<ReceiveRecord> {

    /**
     * 领取记录
     *
     * @param dto 请求参数
     * @return 记录详情
     */
    IPage<ReceiveRecordVo> receiveRecordPageSearch(ReceiveRecordPageSearchDto dto);

    /**
     * 使用记录
     *
     * @param dto 请求参数
     * @return 记录详情
     */
    IPage<UseRecordVo> useRecordPageSearch(UseRecordPageSearchDto dto);

    /**
     * 我的优惠卷列表查询
     *
     * @return 优惠卷详情
     */
//    IPage<MyCouponVo> myCoupon(MyCouponSearchDto dto);

    /**
     * 我可以使用的优惠卷列表查询
     */
//    List<MyCanUseCouponVo> canUse(MyCanUseCouponSearchDto dto);

    /**
     * 我可以使用的优惠卷数量查询
     * @param dto 请求参数
     * @return 优惠卷数量
     */
//    Integer canUseCount(MyCanUseCouponSearchDto dto);

}