package com.sep.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.dto.PointPageSearchDto;
import com.sep.point.dto.ProductsExchangeInput;
import com.sep.point.enums.BizErrorCode;
import com.sep.point.enums.FundChangeType;
import com.sep.point.enums.OneDayOperationLimitCode;
import com.sep.point.enums.PointSettingCode;
import com.sep.point.model.FundChange;
import com.sep.point.model.Point;
import com.sep.point.repository.PointMapper;
import com.sep.point.service.FundChangeService;
import com.sep.point.service.PointService;
import com.sep.point.service.SettingService;
import com.sep.point.vo.PointVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Service
public class PointServiceImpl extends ServiceImpl<PointMapper, Point> implements PointService {

    @Resource
    private SettingService settingService;
    @Resource
    private FundChangeService fundChangeService;

    @Transactional
    @Override
    public boolean increase(PointIncreaseInput input) {
        Point point = getByUserId(input.getUserId());
        if (Objects.isNull(point)) {
            createPoint(input.getUserId());
        }

        LocalDateTime now = LocalDateTime.now();
        Integer pointSetting = settingService.getSettingValue(PointSettingCode.TYPE, input.getFundChangeType());
        if (Objects.isNull(pointSetting)) {
            throw new SepCustomException(BizErrorCode.POINT_SETTING_NOT_FOUND_ERROR);
        }

        if (pointSetting < 1) {
            return true;
        }

        Integer limitSetting = settingService.getSettingValue(OneDayOperationLimitCode.TYPE, input.getFundChangeType());
        int toDayIncreaseCount = fundChangeService.toDayIncreaseCount(input.getUserId(), input.getFundChangeType());

        if (Objects.nonNull(limitSetting) && limitSetting < (toDayIncreaseCount + 1)) {
            return true;
        }

        int current = current(input.getUserId());

        LambdaUpdateWrapper<Point> lambda = new UpdateWrapper<Point>().lambda();
        lambda.set(Point::getUpdateTime, now);
        lambda.setSql("balance = balance + " + pointSetting);
        lambda.eq(Point::getUserId, input.getUserId());

        FundChange fundChange = new FundChange();
        fundChange.setCreateTime(now);
        fundChange.setUpdateTime(now);
        fundChange.setBeforeAmount(current);
        fundChange.setAfterAmount(current + pointSetting);
        fundChange.setAmount(pointSetting);
        fundChange.setType(input.getFundChangeType());
        fundChange.setUserId(input.getUserId());

        if (this.update(lambda) && fundChangeService.save(fundChange)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.POINT_CHANGE_ERROR);
    }

    @Transactional
    @Override
    public boolean productsExchange(ProductsExchangeInput input) {
        if (fundChangeService.isExistByOrderNo(input.getOrderNo())) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        int current = current(input.getUserId());
        if (current < input.getTakePoint()) {
            throw new SepCustomException(BizErrorCode.POINT_LACK_ERROR);
        }

        LambdaUpdateWrapper<Point> lambda = new UpdateWrapper<Point>().lambda();
        lambda.set(Point::getUpdateTime, now);
        lambda.setSql("balance = balance - " + input.getTakePoint());
        lambda.eq(Point::getUserId, input.getUserId());
        lambda.ge(Point::getBalance, input.getTakePoint());

        FundChange fundChange = new FundChange();
        fundChange.setCreateTime(now);
        fundChange.setUpdateTime(now);
        fundChange.setBeforeAmount(current);
        fundChange.setAfterAmount(current - input.getTakePoint());
        fundChange.setAmount(-input.getTakePoint());
        fundChange.setType(FundChangeType.PRODUCTS_EXCHANGE.getCode());
        fundChange.setUserId(input.getUserId());
        fundChange.setOrderId(input.getOrderId());
        fundChange.setOrderNo(input.getOrderNo());

        if (this.update(lambda) && fundChangeService.save(fundChange)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.POINT_CHANGE_ERROR);
    }

    @Override
    public int current(Integer userId) {
        Point point = getByUserId(userId);
        if (Objects.nonNull(point)) {
            return point.getBalance();
        } else {
            return 0;
        }
    }

    @Override
    public IPage<PointVo> pageSearch(PointPageSearchDto dto) {
        LambdaQueryWrapper<Point> lambda = new QueryWrapper<Point>().lambda();
        lambda.orderByDesc(Point::getId);
        if (Objects.nonNull(dto.getUserId())) {
            lambda.eq(Point::getUserId, dto.getUserId());
        }
        IPage<Point> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        List<PointVo> vos = page.getRecords().stream().map(point -> {
            PointVo vo = new PointVo();
            BeanUtils.copyProperties(point, vo);
            return vo;
        }).collect(Collectors.toList());
        IPage<PointVo> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(vos);
        return result;
    }

    private Point getByUserId(Integer userId) {
        LambdaQueryWrapper<Point> lambda = new QueryWrapper<Point>().lambda();
        lambda.select(Point::getBalance);
        lambda.eq(Point::getUserId, userId);
        return getOne(lambda);
    }

    private boolean createPoint(Integer userId) {
        Point point = new Point();
        point.setCreateTime(LocalDateTime.now());
        point.setUpdateTime(LocalDateTime.now());
        point.setUserId(userId);
        point.setBalance(0);
        if (this.save(point)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.POINT_CHANGE_ERROR);
    }

}