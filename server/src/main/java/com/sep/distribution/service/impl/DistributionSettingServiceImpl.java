package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.dto.SetSettingValueDto;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.model.DistributionSetting;
import com.sep.distribution.repository.DistributionSettingMapper;
import com.sep.distribution.service.SettingService;
import com.sep.distribution.vo.background.SettingVo;
import com.sep.common.exceptions.SepCustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-08
 */
@Service
public class DistributionSettingServiceImpl extends ServiceImpl<DistributionSettingMapper, DistributionSetting> implements SettingService {

    public BigDecimal getMinWithdraw() {
        return new BigDecimal(getSettingValue(SETTING_TYPE_WITHDRAW, SETTING_WITHDRAW));
    }

    public String getSettingValue(int type, String name) {
        LambdaQueryWrapper<DistributionSetting> lambda = new QueryWrapper<DistributionSetting>().lambda();
        lambda.eq(DistributionSetting::getType, type);
        lambda.eq(DistributionSetting::getName, name);
        DistributionSetting setting = this.getOne(lambda);
        if (Objects.nonNull(setting)) {
            return setting.getValue();
        }
        return "";
    }

    public boolean setSettingValue(SetSettingValueDto dto) {
        LambdaUpdateWrapper<DistributionSetting> lambda = new UpdateWrapper<DistributionSetting>().lambda();
        lambda.set(DistributionSetting::getValue, dto.getValue());
        lambda.eq(DistributionSetting::getType, dto.getType());
        lambda.eq(DistributionSetting::getName, dto.getName());
        if (this.update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SETTING_ERROR);
    }

    @Override
    public List<SettingVo> findAll() {
        return this.list().stream().map(setting -> {
            SettingVo vo = new SettingVo();
            BeanUtils.copyProperties(setting, vo);
            return vo;
        }).collect(Collectors.toList());
    }

}