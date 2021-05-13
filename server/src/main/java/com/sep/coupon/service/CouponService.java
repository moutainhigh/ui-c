package com.sep.coupon.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.coupon.dto.*;
import com.sep.coupon.model.Coupon;
import com.sep.coupon.vo.CouponDetailsOutPut;
import com.sep.coupon.vo.CouponUnreceivedVo;

/**
 * <p>
 * 优惠卷表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
public interface CouponService extends IService<Coupon> {

//    /**
//     * 分页查询
//     *
//     * @param dto 请求参数
//     * @return 详情
//     */
//    IPage<CouponDetailsOutPut> pageSearch(CouponPageSearchDto dto);
//
//    /**
//     * 添加
//     *
//     * @param dto 请求参数
//     * @return 是否成功
//     */
//    boolean add(CouponAddDto dto);
//
//    /**
//     * 修改
//     *
//     * @param dto 请求参数
//     * @return 是否成功
//     */
//    boolean update(CouponUpdateDto dto);
//
//    /**
//     * 发布
//     *
//     * @param dto 请求参数
//     * @return 是否成功
//     */
//    boolean publish(BaseUpdateDto dto);
//
//    /**
//     * 停发
//     *
//     * @param dto 请求参数
//     * @return 是否成功
//     */
//    boolean suspended(BaseUpdateDto dto);
//
//    /**
//     * 详情查询
//     *
//     * @param dto 请求参数
//     * @return 详情
//     */
//    CouponDetailsOutPut details(Integer dto);
//
//    /**
//     * 首页列表查询
//     *
//     * @return 优惠卷详情
//     */
//    IPage<CouponUnreceivedVo> nominates(NominatesDto dto);
//
//    /**
//     * 可领取列表查询
//     *
//     * @return 优惠卷详情
//     */
//    IPage<CouponUnreceivedVo> unreceived(ReceivedDto dto);
//
//    /**
//     * 领取优惠卷
//     *
//     * @param dto 请求参数
//     * @return 是否成功
//     */
//    boolean receive(ReceiveDto dto);
//
//    /**
//     * 使用优惠卷
//     *
//     * @param input 请求参数
//     * @return 是否成功
//     */
//    boolean use(UseCouponInput input);

}