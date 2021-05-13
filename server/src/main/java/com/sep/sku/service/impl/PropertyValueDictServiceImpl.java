package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.model.PropertyValueDict;
import com.sep.sku.repository.PropertyValueDictMapper;
import com.sep.sku.service.PropertyValueDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性值字典表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class PropertyValueDictServiceImpl extends ServiceImpl<PropertyValueDictMapper, PropertyValueDict> implements PropertyValueDictService {

    @Override
    public List<PropertyValueDict> findPropertyValueDictListByPropertyId(Integer propertyId) {
        if(propertyId == null){
            return Lists.newArrayList();
        }
        QueryWrapper<PropertyValueDict> wrapper = new QueryWrapper<>();
        wrapper.eq("property_id",propertyId);
        return baseMapper.selectList(wrapper);
    }
}
