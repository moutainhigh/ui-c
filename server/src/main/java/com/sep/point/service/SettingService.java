package com.sep.point.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.point.model.Setting;
import com.sep.point.dto.SetSettingValueDto;
import com.sep.point.dto.UpdateSettingsDto;
import com.sep.point.vo.SettingVo;

import java.util.List;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
public interface SettingService extends IService<Setting> {

    /**
     * 查询所有
     *
     * @return 设置信息
     */
    List<SettingVo> findAll();

    /**
     * 设置值
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean setSettingValue(SetSettingValueDto dto);

    /**
     * 获取设置值
     *
     * @param type 设置类型
     * @param name 设置名称
     * @return 设置值
     */
    Integer getSettingValue(int type, Integer name);

    /**
     * 更新设置
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean update(UpdateSettingsDto dto);

}