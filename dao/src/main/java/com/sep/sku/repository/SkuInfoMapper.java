package com.sep.sku.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sep.sku.model.SkuInfo;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    int statisticalSkuCount();

}
