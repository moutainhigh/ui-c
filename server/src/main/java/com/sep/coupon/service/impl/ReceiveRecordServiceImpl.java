package com.sep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.common.enums.YesNo;
import com.sep.coupon.dto.MyCanUseCouponSearchDto;
import com.sep.coupon.dto.MyCouponSearchDto;
import com.sep.coupon.dto.ReceiveRecordPageSearchDto;
import com.sep.coupon.dto.UseRecordPageSearchDto;
import com.sep.coupon.enums.CouponStatus;
import com.sep.coupon.model.Coupon;
import com.sep.coupon.model.ReceiveRecord;
import com.sep.coupon.repository.ReceiveRecordMapper;
import com.sep.coupon.service.CouponService;
import com.sep.coupon.service.ReceiveRecordService;
import com.sep.coupon.vo.MyCanUseCouponVo;
import com.sep.coupon.vo.MyCouponVo;
import com.sep.coupon.vo.ReceiveRecordVo;
import com.sep.coupon.vo.UseRecordVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Service
public class ReceiveRecordServiceImpl extends ServiceImpl<ReceiveRecordMapper, ReceiveRecord> implements ReceiveRecordService {

    @Resource
    private CouponService couponService;

    @Override
    public IPage<ReceiveRecordVo> receiveRecordPageSearch(ReceiveRecordPageSearchDto dto) {
        IPage<ReceiveRecordVo> result = new Page<>();
        //查询优惠券ID集合
        List<Integer> couponIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(dto.getTitle()) || Objects.nonNull(dto.getType())) {
            LambdaQueryWrapper<Coupon> lambda = new QueryWrapper<Coupon>().lambda();
            lambda.select(Coupon::getId);
            if (StringUtils.isNotEmpty(dto.getTitle())) {
                lambda.like(Coupon::getTitle, dto.getTitle());
            }
            if (Objects.nonNull(dto.getType())) {
                lambda.eq(Coupon::getType, dto.getType());
            }
            List<Coupon> coupons = couponService.list(lambda);
            if (CollectionUtils.isEmpty(coupons)) {
                return result;
            }
            couponIds = coupons.stream().map(Coupon::getId).collect(Collectors.toList());
        }

        //构建查询条件
        LambdaQueryWrapper<ReceiveRecord> lambda = new QueryWrapper<ReceiveRecord>().lambda();
        lambda.orderByDesc(ReceiveRecord::getCreateTime);
        if (Objects.nonNull(dto.getUserId())) {
            lambda.eq(ReceiveRecord::getUserId, dto.getUserId());
        }
        if (!couponIds.isEmpty()) {
            lambda.in(ReceiveRecord::getCouponId, couponIds);
        }
        if (Objects.nonNull(dto.getUseStatus())) {
            lambda.eq(ReceiveRecord::getUseStatus, dto.getUseStatus());
        }
        if (Objects.nonNull(dto.getReceiveStartDateTime())) {
            lambda.ge(ReceiveRecord::getCreateTime, dto.getReceiveStartDateTime());
        }
        if (Objects.nonNull(dto.getReceiveEndDateTime())) {
            lambda.le(ReceiveRecord::getCreateTime, dto.getReceiveEndDateTime());
        }

        //执行查询
        IPage<ReceiveRecord> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //查询优惠卷
        List<Integer> ids = page.getRecords().stream().map(ReceiveRecord::getCouponId).collect(Collectors.toList());
        Collection<Coupon> coupons = couponService.listByIds(ids);
        Map<Integer, Coupon> couponMap = coupons.stream().collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
        //结果转换
        List<ReceiveRecordVo> collect = page.getRecords().stream().map(receiveRecord -> {
            ReceiveRecordVo vo = new ReceiveRecordVo();
            Coupon coupon = couponMap.get(receiveRecord.getCouponId());
            BeanUtils.copyProperties(receiveRecord, vo);
            BeanUtils.copyProperties(coupon, vo);
            vo.setReceiveDateTime(receiveRecord.getCreateTime());
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @Override
    public IPage<UseRecordVo> useRecordPageSearch(UseRecordPageSearchDto dto) {
        IPage<UseRecordVo> result = new Page<>();
        //查询优惠券ID集合
        List<Integer> couponIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(dto.getTitle()) || Objects.nonNull(dto.getType())) {
            LambdaQueryWrapper<Coupon> lambda = new QueryWrapper<Coupon>().lambda();
            lambda.select(Coupon::getId);
            if (StringUtils.isNotEmpty(dto.getTitle())) {
                lambda.like(Coupon::getTitle, dto.getTitle());
            }
            if (Objects.nonNull(dto.getType())) {
                lambda.eq(Coupon::getType, dto.getType());
            }
            List<Coupon> coupons = couponService.list(lambda);
            if (CollectionUtils.isEmpty(coupons)) {
                return result;
            }
            couponIds = coupons.stream().map(Coupon::getId).collect(Collectors.toList());
        }

        //构建查询条件
        LambdaQueryWrapper<ReceiveRecord> lambda = new QueryWrapper<ReceiveRecord>().lambda();
        lambda.orderByDesc(ReceiveRecord::getUseDateTime);
        lambda.eq(ReceiveRecord::getUseStatus, YesNo.YES.getCode());
        if (Objects.nonNull(dto.getUserId())) {
            lambda.eq(ReceiveRecord::getUserId, dto.getUserId());
        }
        if (StringUtils.isNotEmpty(dto.getOrderNo())) {
            lambda.eq(ReceiveRecord::getOrderNo, dto.getOrderNo());
        }
        if (!couponIds.isEmpty()) {
            lambda.in(ReceiveRecord::getCouponId, couponIds);
        }
        if (Objects.nonNull(dto.getUseStartDateTime())) {
            lambda.ge(ReceiveRecord::getUseDateTime, dto.getUseStartDateTime());
        }
        if (Objects.nonNull(dto.getUseEndDateTime())) {
            lambda.le(ReceiveRecord::getUseDateTime, dto.getUseEndDateTime());
        }

        //执行查询
        IPage<ReceiveRecord> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //查询优惠卷
        List<Integer> ids = page.getRecords().stream().map(ReceiveRecord::getCouponId).collect(Collectors.toList());
        Collection<Coupon> coupons = couponService.listByIds(ids);
        Map<Integer, Coupon> couponMap = coupons.stream().collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
        //结果转换
        List<UseRecordVo> collect = page.getRecords().stream().map(receiveRecord -> {
            UseRecordVo vo = new UseRecordVo();
            Coupon coupon = couponMap.get(receiveRecord.getCouponId());
            BeanUtils.copyProperties(receiveRecord, vo);
            BeanUtils.copyProperties(coupon, vo);
            return vo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

//    @Override
//    public IPage<MyCouponVo> myCoupon(MyCouponSearchDto dto) {
//        IPage<MyCouponVo> result = new Page<>();
//        //构建查询条件
//        LambdaQueryWrapper<ReceiveRecord> lambda = new QueryWrapper<ReceiveRecord>().lambda();
//        lambda.orderByDesc(ReceiveRecord::getCreateTime);
//        if (Objects.nonNull(dto.getStatus())) {
//            if (CouponStatus.STALE.getCode() == dto.getStatus()) {
//                lambda.lt(ReceiveRecord::getUseEndDate, LocalDate.now());
//                lambda.eq(ReceiveRecord::getUseStatus, CouponStatus.UN_USED.getCode());
//            } else {
//                lambda.eq(ReceiveRecord::getUseStatus, dto.getStatus());
//            }
//        }
//        lambda.eq(ReceiveRecord::getUserId, dto.getUserId());
//        //执行查询
//        IPage<ReceiveRecord> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
//        BeanUtils.copyProperties(page, result);
//        if (CollectionUtils.isEmpty(page.getRecords())) {
//            return result;
//        }
//
//        //查询优惠卷
//        List<Integer> ids = page.getRecords().stream().map(ReceiveRecord::getCouponId).collect(Collectors.toList());
//        Collection<Coupon> coupons = couponService.listByIds(ids);
//        Map<Integer, Coupon> couponMap = coupons.stream().collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
//        //结果转换
//        List<MyCouponVo> collect = page.getRecords().stream().map(receiveRecord -> {
//            MyCouponVo vo = new MyCouponVo();
//            Coupon coupon = couponMap.get(receiveRecord.getCouponId());
//            BeanUtils.copyProperties(receiveRecord, vo);
//            BeanUtils.copyProperties(coupon, vo);
//            vo.setStatus(receiveRecord.getUseStatus());
//            if (coupon.getUseEndDate().isBefore(LocalDate.now()) && CouponStatus.UN_USED.getCode() == vo.getStatus()) {
//                vo.setStatus(CouponStatus.STALE.getCode());
//            }
//            return vo;
//        }).collect(Collectors.toList());
//        result.setRecords(collect);
//        return result;
//    }
//
//    @Override
//    public List<MyCanUseCouponVo> canUse(MyCanUseCouponSearchDto dto) {
//        List<MyCanUseCouponVo> result = Lists.newArrayList();
//        //执行查询
//        List<ReceiveRecord> receiveRecords = list(canUseQuery(dto));
//        if (CollectionUtils.isEmpty(receiveRecords)) {
//            return result;
//        }
//
//        //查询优惠卷
//        List<Integer> ids = receiveRecords.stream().map(ReceiveRecord::getCouponId).collect(Collectors.toList());
//        Collection<Coupon> coupons = couponService.listByIds(ids);
//        Map<Integer, Coupon> couponMap = coupons.stream().collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
//        //结果转换
//        return receiveRecords.stream().map(receiveRecord -> {
//            MyCanUseCouponVo vo = new MyCanUseCouponVo();
//            Coupon coupon = couponMap.get(receiveRecord.getCouponId());
//            BeanUtils.copyProperties(receiveRecord, vo);
//            BeanUtils.copyProperties(coupon, vo);
//            return vo;
//        }).collect(Collectors.toList());
//    }
//
//    @Override
//    public Integer canUseCount(MyCanUseCouponSearchDto dto) {
//        return count(canUseQuery(dto));
//    }
//
//    private LambdaQueryWrapper<ReceiveRecord> canUseQuery(MyCanUseCouponSearchDto dto) {
//        LocalDate now = LocalDate.now();
//        //构建查询条件
//        LambdaQueryWrapper<ReceiveRecord> lambda = new QueryWrapper<ReceiveRecord>().lambda();
//        lambda.orderByDesc(ReceiveRecord::getCashRebate);
//        lambda.le(ReceiveRecord::getUseStartDate, now);
//        lambda.ge(ReceiveRecord::getUseEndDate, now);
//        lambda.eq(ReceiveRecord::getUseStatus, CouponStatus.UN_USED.getCode());
//        lambda.eq(ReceiveRecord::getUserId, dto.getUserId());
//        lambda.le(ReceiveRecord::getMonetary, dto.getMonetary());
//        return lambda;
//    }

}