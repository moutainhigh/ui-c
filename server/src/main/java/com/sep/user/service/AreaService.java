package com.sep.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.user.model.Area;
import com.sep.user.vo.AreaVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
public interface AreaService extends IService<Area> {


    Area getArea(Long id);

    List<AreaVo> getLevel1();

    List<AreaVo> getLevel2();

    List<AreaVo> getLevel3();

    Area getAreaCode(String cityName);




}
