package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.SkuParam;

import java.util.List;

/**
 * <p>
 * sku参数表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface SkuParamService extends IService<SkuParam> {

    /**
     * 根据商品id查询商品参数集合
     * @param skuId
     * @return
     */
    List<SkuParam> findSkuParamListBySkuId(Integer skuId);

}
