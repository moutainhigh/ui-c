package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.bean.SkuPropertyInfo;
import com.sep.sku.bean.SkuPropertyValueInfo;
import com.sep.sku.dto.SearchPropertyDictDto;
import com.sep.sku.model.PropertyDict;
import com.sep.sku.model.PropertyValueDict;
import com.sep.sku.repository.PropertyDictMapper;
import com.sep.sku.service.PropertyDictService;
import com.sep.sku.service.PropertyValueDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性字典表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class PropertyDictServiceImpl extends ServiceImpl<PropertyDictMapper, PropertyDict> implements PropertyDictService {

    @Autowired
    private PropertyValueDictService propertyValueDictService;

//    @Override
//    public List<SkuPropertyInfo> getAllPropertyDictInfo(SearchPropertyDictDto searchPropertyDictDto) {
//        QueryWrapper<PropertyDict> propertyDictQueryWrapper = new QueryWrapper<>();
//        if(searchPropertyDictDto.getCategoryId() != null){
//            propertyDictQueryWrapper.eq("category_id",searchPropertyDictDto.getCategoryId());
//        }
//        List<PropertyDict> propertyDictList = baseMapper.selectList(propertyDictQueryWrapper);
//        if(CollectionUtils.isEmpty(propertyDictList)){
//            return Lists.newArrayList();
//        }
//        List<SkuPropertyInfo> skuPropertyInfoList = Lists.newArrayList();
//        propertyDictList.forEach(e->{
//            SkuPropertyInfo skuPropertyInfo = new SkuPropertyInfo();
//            skuPropertyInfo.setPropertyDictId(e.getId());
//            skuPropertyInfo.setPropertyName(e.getPropertyName());
//            List<PropertyValueDict> valueDictList = propertyValueDictService.findPropertyValueDictListByPropertyId(e.getId());
//            if(!CollectionUtils.isEmpty(valueDictList)){
//                List<SkuPropertyValueInfo> skuPropertyValueInfoList = valueDictList.stream().map(valueDict ->{
//                    SkuPropertyValueInfo valueInfo = new SkuPropertyValueInfo();
//                    valueInfo.setPropertyValueDictId(valueDict.getId());
//                    valueInfo.setPropertyValueName(valueDict.getPropertyValue());
//                    return valueInfo;
//                }).collect(Collectors.toList());
//                skuPropertyInfo.setPropertyValueList(skuPropertyValueInfoList);
//            }
//            skuPropertyInfoList.add(skuPropertyInfo);
//        });
//        return skuPropertyInfoList;
//    }
}
