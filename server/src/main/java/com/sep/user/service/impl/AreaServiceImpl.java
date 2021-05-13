package com.sep.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sep.user.model.Area;
import com.sep.user.repository.AreaMapper;
import com.sep.user.service.AreaService;
import com.sep.user.vo.AreaVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public Area getArea(Long id) {
        Area area = getById(id);
        if (area == null)
            return null;
        return area;
    }

    @Override
    public List<AreaVo> getLevel1() {
        return lambdaQuery().isNull(Area::getParent).list().stream().map(e -> new AreaVo(e.getId(), e.getName(), e.getParent())).collect(Collectors.toList());
    }


    @Override
    public List<AreaVo> getLevel2() {
        return lambdaQuery().in(Area::getParent, lambdaQuery().isNull(Area::getParent).list().stream().map(Area::getId).collect(Collectors.toList()))
                .list().stream().map(e -> new AreaVo(e.getId(), e.getName(), e.getParent())).collect(Collectors.toList());
    }

    @Override
    public List<AreaVo> getLevel3() {
        return null;
    }

    @Override
    public Area getAreaCode(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }
        List<Area> list = lambdaQuery().eq(Area::getName, cityName).list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
