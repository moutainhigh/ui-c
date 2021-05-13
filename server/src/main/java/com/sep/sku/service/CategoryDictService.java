package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.dto.CategoryDictSaveDto;
import com.sep.sku.dto.CategoryDictUpdateDto;
import com.sep.sku.model.CategoryDict;

import java.util.List;

/**
 * <p>
 * 商品分类字典表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface CategoryDictService extends IService<CategoryDict> {

    List<CategoryDict> getAllSkuCategory();

    List<CategoryDict> getSkuCategoryForXcx();

    int saveSkuCategory(CategoryDictSaveDto categoryDictSaveDto);

    int updateById(CategoryDictUpdateDto categoryDictUpdateDto);

}
