package com.sep.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.point.model.Setting;
import com.sep.point.repository.SettingMapper;
import com.sep.point.service.SettingService;
import com.sep.point.dto.SetSettingValueDto;
import com.sep.point.dto.UpdateSettingsDto;
import com.sep.point.enums.BizErrorCode;
import com.sep.point.vo.SettingVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Override
    public List<SettingVo> findAll() {
        return this.list().stream().map(setting -> {
            SettingVo vo = new SettingVo();
            BeanUtils.copyProperties(setting, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    public boolean setSettingValue(SetSettingValueDto dto) {
        LambdaUpdateWrapper<Setting> lambda = new UpdateWrapper<Setting>().lambda();
        lambda.set(Setting::getUpdateUid, dto.getUpdateUid());
        lambda.set(Setting::getUpdateTime, LocalDateTime.now());
        lambda.set(Setting::getValue, dto.getValue());
        lambda.eq(Setting::getType, dto.getType());
        lambda.eq(Setting::getCode, dto.getCode());
        if (this.update(lambda)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SETTING_ERROR);
    }

    public Integer getSettingValue(int type, Integer code) {
        LambdaQueryWrapper<Setting> lambda = new QueryWrapper<Setting>().lambda();
        lambda.eq(Setting::getType, type);
        lambda.eq(Setting::getCode, code);
        Setting setting = this.getOne(lambda);
        if (Objects.nonNull(setting)) {
            return setting.getValue();
        }
        return null;
    }

    @Override
    public boolean update(UpdateSettingsDto dto) {
        List<Setting> collect = dto.getSettings().stream().map(settingDto -> {
            Setting setting = new Setting();
            setting.setId(settingDto.getId());
            setting.setValue(settingDto.getValue());
            setting.setUpdateUid(dto.getUpdateUid());
            setting.setUpdateTime(LocalDateTime.now());
            return setting;
        }).collect(Collectors.toList());
        if (updateBatchById(collect)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.SETTING_ERROR);
    }

}