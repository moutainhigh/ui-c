package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.PropertyValueDict;

import java.util.List;

/**
 * <p>
 * 商品属性值字典表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface PropertyValueDictService extends IService<PropertyValueDict> {

    List<PropertyValueDict> findPropertyValueDictListByPropertyId(Integer propertyId);
}
