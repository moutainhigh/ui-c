package com.sep.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.utils.DateUtils;
import com.sep.point.dto.PageSearchFundChangeDto;
import com.sep.point.dto.SearchFundChangeDto;
import com.sep.point.enums.FundChangeType;
import com.sep.point.model.FundChange;
import com.sep.point.repository.FundChangeMapper;
import com.sep.point.service.FundChangeService;
import com.sep.point.vo.FundChangeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 积分变动表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Service
public class FundChangeServiceImpl extends ServiceImpl<FundChangeMapper, FundChange> implements FundChangeService {

    @Resource
    private FundChangeMapper fundChangeMapper;

    @Override
    public int toDayIncreaseCount(Integer userId, int fundChangeType) {
        LambdaQueryWrapper<FundChange> lambda = new QueryWrapper<FundChange>().lambda();
        lambda.eq(FundChange::getUserId, userId);
        lambda.eq(FundChange::getType, fundChangeType);
        return this.count(lambda);
    }

    @Override
    public int toDayEarnings(Integer userId) {
        LocalDate now = LocalDate.now();
        return getPointByDateTimeRange(userId, DateUtils.getDayFirstTime(now), DateUtils.getDayEndTime(now));
    }

    @Override
    public int weekendEarnings(Integer userId) {
        LocalDate now = LocalDate.now();
        return getPointByDateTimeRange(userId, DateUtils.getWeekFirstTime(now), DateUtils.getWeekLastTime(now));
    }

    @Override
    public int currentMonthEarnings(Integer userId) {
        LocalDate now = LocalDate.now();
        return getPointByDateTimeRange(userId, DateUtils.getMonthFirstTime(now), DateUtils.getMonthLastTime(now));
    }

    @Override
    public IPage<FundChangeVo> pageSearch(PageSearchFundChangeDto dto) {
        LambdaQueryWrapper<FundChange> wrapper = new QueryWrapper<FundChange>().lambda();
        if (Objects.nonNull(dto.getUserId())) {
            wrapper.eq(FundChange::getUserId, dto.getUserId());
        }
        if (Objects.nonNull(dto.getFundChangeType())) {
            wrapper.eq(FundChange::getType, dto.getFundChangeType());
        }
        if (Objects.nonNull(dto.getStartTime())) {
            wrapper.ge(FundChange::getCreateTime, dto.getStartTime());
        }
        if (Objects.nonNull(dto.getEndTime())) {
            wrapper.le(FundChange::getCreateTime, dto.getEndTime());
        }
        wrapper.orderByDesc(FundChange::getId);
        IPage<FundChange> fundChangeIPage = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), wrapper);
        List<FundChangeVo> vos = fundChangeIPage.getRecords().stream().map(fundChange -> {
            FundChangeVo vo = new FundChangeVo();
            BeanUtils.copyProperties(fundChange, vo);
            vo.setFundChangeType(FundChangeType.valueOf(fundChange.getType()).getDescription());
            return vo;
        }).collect(Collectors.toList());
        IPage<FundChangeVo> result = new Page<>();
        BeanUtils.copyProperties(fundChangeIPage, result);
        result.setRecords(vos);
        return result;
    }

    @Override
    public IPage<FundChangeVo> pageSearch(SearchFundChangeDto dto) {
        LambdaQueryWrapper<FundChange> wrapper = new QueryWrapper<FundChange>().lambda();
        wrapper.eq(FundChange::getUserId, dto.getUserId());
        if (Objects.nonNull(dto.getFundChangeType())) {
            wrapper.eq(FundChange::getType, dto.getFundChangeType());
        }
        wrapper.ge(FundChange::getCreateTime, dto.getStartTime());
        wrapper.le(FundChange::getCreateTime, dto.getEndTime());
        wrapper.orderByDesc(FundChange::getId);
        IPage<FundChange> fundChangeIPage = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), wrapper);
        List<FundChangeVo> vos = fundChangeIPage.getRecords().stream().map(fundChange -> {
            FundChangeVo vo = new FundChangeVo();
            BeanUtils.copyProperties(fundChange, vo);
            vo.setFundChangeType(FundChangeType.valueOf(fundChange.getType()).getDescription());
            return vo;
        }).collect(Collectors.toList());
        IPage<FundChangeVo> result = new Page<>();
        BeanUtils.copyProperties(fundChangeIPage, result);
        result.setRecords(vos);
        return result;
    }

    /**
     * 根据时间查询
     *
     * @param userId 用户ID
     * @return 增加积分
     */
    public int getPointByDateTimeRange(Integer userId, LocalDateTime start, LocalDateTime end) {
        LambdaQueryWrapper<FundChange> lambda = new QueryWrapper<FundChange>().lambda();
        lambda.eq(FundChange::getUserId, userId);
        lambda.gt(FundChange::getAmount, 0);
        lambda.ge(FundChange::getCreateTime, start);
        lambda.le(FundChange::getCreateTime, end);
        return fundChangeMapper.sumAmount(lambda);
    }

    public boolean isExistByOrderNo(String orderNo) {
        LambdaQueryWrapper<FundChange> lambda = new QueryWrapper<FundChange>().lambda();
        lambda.eq(FundChange::getOrderNo, orderNo);
        return this.count(lambda) > 0;
    }

}