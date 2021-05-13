package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.model.SkuProperty;
import com.sep.sku.repository.SkuPropertyMapper;
import com.sep.sku.service.SkuPropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku属性规则表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class SkuPropertyServiceImpl extends ServiceImpl<SkuPropertyMapper, SkuProperty> implements SkuPropertyService {

    @Override
    public List<SkuProperty> findSkuPropertyListBySkuId(Integer skuId) {

        List<SkuProperty> skuPropertyList = Lists.newArrayList();
        if(skuId == null){
            return skuPropertyList;
        }
        QueryWrapper<SkuProperty> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id",skuId);
        return baseMapper.selectList(wrapper);
    }
}
