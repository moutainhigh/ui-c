package com.sep.distribution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.SetSettingValueDto;
import com.sep.distribution.model.DistributionSetting;
import com.sep.distribution.vo.background.SettingVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-08
 */
public interface SettingService extends IService<DistributionSetting> {

    /**
     * 提现设置
     */
    int SETTING_TYPE_WITHDRAW = 0;

    /**
     * 最小提现金额
     */
    String SETTING_WITHDRAW = "min_withdraw";

    /**
     * 获取最小提现金额
     *
     * @return
     */
    BigDecimal getMinWithdraw();

    /**
     * 获取设置值
     *
     * @param type 设置类型
     * @param name 设置名称
     * @return 设置值
     */
    String getSettingValue(int type, String name);

    /**
     * 设置值
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean setSettingValue(SetSettingValueDto dto);

    /**
     * 查询所有
     *
     * @return 设置信息
     */
    List<SettingVo> findAll();

}