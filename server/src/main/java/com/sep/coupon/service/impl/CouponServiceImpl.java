package com.sep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.enums.YesNo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.coupon.dto.*;
import com.sep.coupon.enums.BizErrorCode;
import com.sep.coupon.enums.PublishStatus;
import com.sep.coupon.model.Coupon;
import com.sep.coupon.model.ReceiveRecord;
import com.sep.coupon.repository.CouponMapper;
import com.sep.coupon.service.CouponService;
import com.sep.coupon.service.ReceiveRecordService;
import com.sep.coupon.validator.CouponAddValidator;
import com.sep.coupon.validator.CouponPublishValidator;
import com.sep.coupon.validator.CouponReceiveValidator;
import com.sep.coupon.validator.CouponUpdateValidator;
import com.sep.coupon.vo.CouponDetailsOutPut;
import com.sep.coupon.vo.CouponUnreceivedVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠卷表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Resource
    private ReceiveRecordService receiveRecordService;

    @Resource
    private CouponAddValidator couponAddValidator;
    @Resource
    private CouponUpdateValidator couponUpdateValidator;
    @Resource
    private CouponPublishValidator couponPublishValidator;
    @Resource
    private CouponReceiveValidator couponReceiveValidator;

//    @Override
//    public IPage<CouponDetailsOutPut> pageSearch(CouponPageSearchDto dto) {
//        IPage<CouponDetailsOutPut> result = new Page<>();
//        //构建查询条件
//        LambdaQueryWrapper<Coupon> lambda = new QueryWrapper<Coupon>().lambda();
//        lambda.orderByDesc(Coupon::getCreateTime);
//        if (StringUtils.isNotEmpty(dto.getTitle())) {
//            lambda.like(Coupon::getTitle, dto.getTitle());
//        }
//        if (Objects.nonNull(dto.getType())) {
//            lambda.eq(Coupon::getType, dto.getType());
//        }
//        if (Objects.nonNull(dto.getPublishStatus())) {
//            lambda.eq(Coupon::getPublishStatus, dto.getPublishStatus());
//        }
//        if (Objects.nonNull(dto.getStartDateTime())) {
//            lambda.ge(Coupon::getReceiveStartDate, dto.getStartDateTime());
//        }
//        if (Objects.nonNull(dto.getEndDateTime())) {
//            lambda.le(Coupon::getReceiveStartDate, dto.getEndDateTime());
//        }
//        if (Objects.nonNull(dto.getExpiresStatus())) {
//            if (YesNo.NO.getCode() == dto.getExpiresStatus()) {
//                lambda.ge(Coupon::getReceiveEndDate, LocalDate.now());
//            } else {
//                lambda.lt(Coupon::getReceiveEndDate, LocalDate.now());
//            }
//        }
//        if (Objects.nonNull(dto.getReceiveStatus())) {
//            if (YesNo.NO.getCode() == dto.getReceiveStatus()) {
//                lambda.gt(Coupon::getInventory, 0);
//            } else {
//                lambda.le(Coupon::getInventory, 0);
//            }
//        }
//        //执行查询
//        IPage<Coupon> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
//        //结果转换
//        List<CouponDetailsOutPut> collect = page.getRecords().stream().map(coupon -> {
//            CouponDetailsOutPut vo = new CouponDetailsOutPut();
//            BeanUtils.copyProperties(coupon, vo);
//            vo.setReceive(coupon.getTotal() - coupon.getInventory());
//            if (coupon.getReceiveEndDate().isBefore(LocalDate.now())) {
//                vo.setExpiresStatus(YesNo.YES.getCode());
//            } else {
//                vo.setExpiresStatus(YesNo.NO.getCode());
//            }
//            if (coupon.getInventory() > 0) {
//                vo.setReceiveStatus(YesNo.NO.getCode());
//            } else {
//                vo.setReceiveStatus(YesNo.YES.getCode());
//            }
//            return vo;
//        }).collect(Collectors.toList());
//        BeanUtils.copyProperties(page, result);
//        result.setRecords(collect);
//        return result;
//    }
//
//    @Override
//    public boolean add(CouponAddDto dto) {
//        couponAddValidator.validator(dto);
//        LocalDateTime now = LocalDateTime.now();
//        Coupon coupon = new Coupon();
//        BeanUtils.copyProperties(dto, coupon);
//        coupon.setCreateTime(now);
//        coupon.setUpdateTime(now);
//        coupon.setUpdateUid(dto.getCreateUid());
//        coupon.setInventory(dto.getTotal());
//        coupon.setUsed(0);
//        coupon.setPublishStatus(PublishStatus.UNPUBLISHED.getCode());
//        coupon.setTitle(getTitle(dto.getTitle(), dto.getMonetary(), dto.getCashRebate()));
//        if (save(coupon)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_ADD_ERROR);
//    }
//
//    @Override
//    public boolean update(CouponUpdateDto dto) {
//        couponUpdateValidator.validator(dto);
//        Coupon coupon = new Coupon();
//        BeanUtils.copyProperties(dto, coupon);
//        coupon.setUpdateTime(LocalDateTime.now());
//        coupon.setInventory(dto.getTotal());
//        coupon.setTitle(getTitle(dto.getTitle(), dto.getMonetary(), dto.getCashRebate()));
//        LambdaUpdateWrapper<Coupon> lambda = new UpdateWrapper<Coupon>().lambda();
//        lambda.eq(Coupon::getId, dto.getId());
//        lambda.eq(Coupon::getPublishStatus, PublishStatus.UNPUBLISHED.getCode());
//        if (update(coupon, lambda)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_UPDATE_ERROR);
//    }
//
//    @Override
//    public boolean publish(BaseUpdateDto dto) {
//        couponPublishValidator.validator(dto);
//        LambdaUpdateWrapper<Coupon> lambda = new UpdateWrapper<Coupon>().lambda();
//        lambda.set(Coupon::getPublishStatus, PublishStatus.PUBLISHED.getCode());
//        lambda.set(Coupon::getUpdateUid, dto.getUpdateUid());
//        lambda.set(Coupon::getUpdateTime, LocalDateTime.now());
//        lambda.eq(Coupon::getId, dto.getId());
//        lambda.eq(Coupon::getPublishStatus, PublishStatus.UNPUBLISHED.getCode());
//        if (update(lambda)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_PUBLISHED_ERROR);
//    }
//
//    @Override
//    public boolean suspended(BaseUpdateDto dto) {
//        LambdaUpdateWrapper<Coupon> lambda = new UpdateWrapper<Coupon>().lambda();
//        lambda.set(Coupon::getPublishStatus, PublishStatus.SUSPENDED.getCode());
//        lambda.set(Coupon::getUpdateUid, dto.getUpdateUid());
//        lambda.set(Coupon::getUpdateTime, LocalDateTime.now());
//        lambda.eq(Coupon::getId, dto.getId());
//        if (update(lambda)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_SUSPENDED_ERROR);
//    }
//
//    @Override
//    public CouponDetailsOutPut details(Integer dto) {
//        Coupon coupon = getById(dto);
//        CouponDetailsOutPut vo = new CouponDetailsOutPut();
//        BeanUtils.copyProperties(coupon, vo);
//        vo.setReceive(coupon.getTotal() - coupon.getInventory());
//        return vo;
//    }
//
//    @Override
//    public IPage<CouponUnreceivedVo> nominates(NominatesDto dto) {
//        LocalDate now = LocalDate.now();
//        IPage<CouponUnreceivedVo> result = new Page<>();
//        LambdaQueryWrapper<Coupon> lambda = new QueryWrapper<Coupon>().lambda();
//        lambda.orderByDesc(Coupon::getCreateTime);
//        lambda.eq(Coupon::getPublishStatus, PublishStatus.PUBLISHED.getCode());
//        lambda.gt(Coupon::getInventory, 0);
//        lambda.le(Coupon::getReceiveStartDate, now);
//        lambda.ge(Coupon::getReceiveEndDate, now);
//        //执行查询
//        IPage<Coupon> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
//        BeanUtils.copyProperties(page, result);
//        if (CollectionUtils.isEmpty(page.getRecords())) {
//            return result;
//        }
//
//        List<Integer> ids = page.getRecords().stream().map(Coupon::getId).collect(Collectors.toList());
//        Set<Integer> receivedIds = getReceivedIds(ids, dto.getUserId());
//
//        //结果转换
//        List<CouponUnreceivedVo> collect = page.getRecords().stream().map(coupon -> {
//            CouponUnreceivedVo vo = new CouponUnreceivedVo();
//            BeanUtils.copyProperties(coupon, vo);
//            vo.setReceived(receivedIds.contains(coupon.getId()));
//            return vo;
//        }).collect(Collectors.toList());
//        result.setRecords(collect);
//        return result;
//    }
//
//    @Override
//    public IPage<CouponUnreceivedVo> unreceived(ReceivedDto dto) {
//        LocalDate now = LocalDate.now();
//        IPage<CouponUnreceivedVo> result = new Page<>();
//        LambdaQueryWrapper<Coupon> lambda = new QueryWrapper<Coupon>().lambda();
//        lambda.orderByDesc(Coupon::getCreateTime);
//        lambda.eq(Coupon::getPublishStatus, PublishStatus.PUBLISHED.getCode());
//        lambda.gt(Coupon::getInventory, 0);
//        lambda.le(Coupon::getReceiveStartDate, now);
//        lambda.ge(Coupon::getReceiveEndDate, now);
//        //执行查询
//        IPage<Coupon> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
//        BeanUtils.copyProperties(page, result);
//        if (CollectionUtils.isEmpty(page.getRecords())) {
//            return result;
//        }
//
//        List<Integer> ids = page.getRecords().stream().map(Coupon::getId).collect(Collectors.toList());
//        Set<Integer> receivedIds = getReceivedIds(ids, dto.getUserId());
//
//        //结果转换
//        List<CouponUnreceivedVo> collect = page.getRecords().stream().map(coupon -> {
//            CouponUnreceivedVo vo = new CouponUnreceivedVo();
//            BeanUtils.copyProperties(coupon, vo);
//            vo.setReceived(receivedIds.contains(coupon.getId()));
//            return vo;
//        }).collect(Collectors.toList());
//        result.setRecords(collect);
//        return result;
//    }
//
//    private Set<Integer> getReceivedIds(List<Integer> ids, Integer userId) {
//        LambdaQueryWrapper<ReceiveRecord> recordLambda = new QueryWrapper<ReceiveRecord>().lambda();
//        recordLambda.select(ReceiveRecord::getCouponId);
//        recordLambda.eq(ReceiveRecord::getUserId, userId);
//        recordLambda.in(ReceiveRecord::getCouponId, ids);
//        return receiveRecordService.list(recordLambda).stream().map(ReceiveRecord::getCouponId).collect(Collectors.toSet());
//    }
//
//    @Transactional
//    @Override
//    public boolean receive(ReceiveDto dto) {
//        couponReceiveValidator.validator(dto);
//        LocalDateTime now = LocalDateTime.now();
//        Coupon coupon = getById(dto.getCouponId());
//        ReceiveRecord receiveRecord = new ReceiveRecord();
//        BeanUtils.copyProperties(coupon, receiveRecord);
//        receiveRecord.setId(null);
//        receiveRecord.setUserId(dto.getUserId());
//        receiveRecord.setCouponId(dto.getCouponId());
//        receiveRecord.setCreateTime(now);
//        receiveRecord.setUpdateTime(now);
//        receiveRecord.setUseStatus(YesNo.NO.getCode());
//
//        LambdaUpdateWrapper<Coupon> couponWrapper = new UpdateWrapper<Coupon>().lambda();
//        couponWrapper.set(Coupon::getUpdateTime, now);
//        couponWrapper.setSql("inventory = inventory - 1");
//        couponWrapper.eq(Coupon::getId, dto.getCouponId());
//        couponWrapper.eq(Coupon::getPublishStatus, PublishStatus.PUBLISHED.getCode());
//        couponWrapper.gt(Coupon::getInventory, 0);
//        couponWrapper.le(Coupon::getReceiveStartDate, now.toLocalDate());
//        couponWrapper.ge(Coupon::getReceiveEndDate, now.toLocalDate());
//
//        if (update(couponWrapper) && receiveRecordService.save(receiveRecord)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_RCEIVE_ERROR);
//    }
//
//    @Transactional
//    @Override
//    public boolean use(UseCouponInput input) {
//        LocalDateTime now = LocalDateTime.now();
//        LambdaUpdateWrapper<ReceiveRecord> receiveRecordWrapper = new UpdateWrapper<ReceiveRecord>().lambda();
//        ReceiveRecord receiveRecord = new ReceiveRecord();
//        receiveRecord.setUpdateTime(now);
//        receiveRecord.setUseStatus(YesNo.YES.getCode());
//        receiveRecord.setUseDateTime(input.getConsumeTime());
//        receiveRecord.setOrderId(input.getOrderId());
//        receiveRecord.setOrderNo(input.getOrderNo());
//        receiveRecord.setActualMonetary(input.getActualMonetary());
//        receiveRecord.setMonetaryTotal(input.getMonetary());
//
//        receiveRecordWrapper.eq(ReceiveRecord::getUserId, input.getConsumer());
//        receiveRecordWrapper.eq(ReceiveRecord::getCouponId, input.getCouponId());
//        receiveRecordWrapper.eq(ReceiveRecord::getUseStatus, YesNo.NO.getCode());
//        receiveRecordWrapper.le(ReceiveRecord::getUseStartDate, input.getConsumeTime().toLocalDate());
//        receiveRecordWrapper.ge(ReceiveRecord::getUseEndDate, input.getConsumeTime().toLocalDate());
//        receiveRecordWrapper.le(ReceiveRecord::getMonetary, input.getMonetary());
//
//        LambdaUpdateWrapper<Coupon> couponWrapper = new UpdateWrapper<Coupon>().lambda();
//        couponWrapper.set(Coupon::getUpdateTime, now);
//        couponWrapper.setSql("used = used + 1");
//        couponWrapper.eq(Coupon::getId, input.getCouponId());
//        couponWrapper.le(Coupon::getUseStartDate, input.getConsumeTime().toLocalDate());
//        couponWrapper.ge(Coupon::getUseEndDate, input.getConsumeTime().toLocalDate());
//
//        if (update(couponWrapper) && receiveRecordService.update(receiveRecord, receiveRecordWrapper)) {
//            return true;
//        }
//        throw new SepCustomException(BizErrorCode.COUPON_USE_ERROR);
//    }
//
//    /**
//     * 获取名称
//     *
//     * @param title      名称
//     * @param monetary   满
//     * @param cashRebate 减
//     * @return 名称
//     */
//    private String getTitle(String title, BigDecimal monetary, BigDecimal cashRebate) {
//        if (StringUtils.isNotEmpty(title)) {
//            return title;
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append("满")
//                .append(monetary)
//                .append("元减")
//                .append(cashRebate).append("元");
//        return builder.toString();
//    }

}