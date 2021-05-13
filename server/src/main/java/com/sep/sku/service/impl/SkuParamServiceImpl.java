package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.model.SkuParam;
import com.sep.sku.repository.SkuParamMapper;
import com.sep.sku.service.SkuParamService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku参数表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class SkuParamServiceImpl extends ServiceImpl<SkuParamMapper, SkuParam> implements SkuParamService {

    @Override
    public List<SkuParam> findSkuParamListBySkuId(Integer skuId) {
        List<SkuParam> skuParamList = Lists.newArrayList();
        if(skuId == null){
            return skuParamList;
        }
        QueryWrapper<SkuParam> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id",skuId);
        return baseMapper.selectList(wrapper);
    }
}
