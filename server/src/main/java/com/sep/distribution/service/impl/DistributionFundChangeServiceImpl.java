package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.dto.SearchFundChangeDto;
import com.sep.distribution.model.DistributionFundChange;
import com.sep.distribution.repository.DistributionFundChangeMapper;
import com.sep.distribution.service.FundChangeService;
import com.sep.distribution.vo.xcx.FundChangeDetailsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 资金变动表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class DistributionFundChangeServiceImpl extends ServiceImpl<DistributionFundChangeMapper, DistributionFundChange> implements FundChangeService {

    @Override
    public IPage<FundChangeDetailsVo> pageSearch(SearchFundChangeDto dto) {
        LambdaQueryWrapper<DistributionFundChange> wrapper = new QueryWrapper<DistributionFundChange>().lambda();
        wrapper.orderByDesc(DistributionFundChange::getId);
        wrapper.eq(DistributionFundChange::getUserId, dto.getUserId());
        if (Objects.nonNull(dto.getFundChangeType())) {
            wrapper.eq(DistributionFundChange::getType, dto.getFundChangeType());
        }
        wrapper.ge(DistributionFundChange::getCreateTime, dto.getStartTime());
        wrapper.le(DistributionFundChange::getCreateTime, dto.getStartEnd());
        wrapper.orderByDesc(DistributionFundChange::getId);
        IPage<DistributionFundChange> fundChangeIPage = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), wrapper);
        List<FundChangeDetailsVo> vos = fundChangeIPage.getRecords().stream().map(fundChange -> {
            FundChangeDetailsVo vo = new FundChangeDetailsVo();
            vo.setFundChangeType(fundChange.getType());
            BeanUtils.copyProperties(fundChange, vo);
            return vo;
        }).collect(Collectors.toList());
        IPage<FundChangeDetailsVo> result = new Page<>();
        BeanUtils.copyProperties(fundChangeIPage, result);
        result.setRecords(vos);
        return result;
    }

    @Override
    public boolean add(Integer type, BigDecimal beforeAamount, BigDecimal amount, Integer userId) {
        DistributionFundChange fundChange = new DistributionFundChange();
        fundChange.setCreateTime(LocalDateTime.now());
        fundChange.setUpdateTime(LocalDateTime.now());
        fundChange.setType(type);
        fundChange.setBeforeAmount(beforeAamount);
        fundChange.setAmount(amount);
        fundChange.setAfterAmount(beforeAamount.add(amount));
        fundChange.setUserId(userId);
        return this.save(fundChange);
    }

}