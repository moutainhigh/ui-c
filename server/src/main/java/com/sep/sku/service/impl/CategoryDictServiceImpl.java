package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.common.enums.YesNo;
import com.sep.sku.dto.CategoryDictSaveDto;
import com.sep.sku.dto.CategoryDictUpdateDto;
import com.sep.sku.model.CategoryDict;
import com.sep.sku.repository.CategoryDictMapper;
import com.sep.sku.service.CategoryDictService;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品分类字典表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class CategoryDictServiceImpl extends ServiceImpl<CategoryDictMapper, CategoryDict> implements CategoryDictService {

    @Override
    public List<CategoryDict> getAllSkuCategory() {
        QueryWrapper<CategoryDict> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("serial");
        List<CategoryDict> categoryDictList = baseMapper.selectList(wrapper);
        return categoryDictList == null ? Lists.newArrayList() : categoryDictList;
    }

    @Override
    public List<CategoryDict> getSkuCategoryForXcx() {
        return lambdaQuery().eq(CategoryDict::getIsDisplay, YesNo.YES.getCode()).orderByDesc(CategoryDict::getSerial).list();
    }

    @Override
    public int saveSkuCategory(CategoryDictSaveDto categoryDictSaveDto) {
        if(categoryDictSaveDto == null){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"商品分类信息为空!");
        }
        CategoryDict categoryDict = new CategoryDict();
        LocalDateTime now = LocalDateTime.now();
        String adminUserName = ""; // todo 获取登录管理员用户名
        BeanUtils.copyProperties(categoryDictSaveDto,categoryDict);
        categoryDict.setCreateTime(now);
        categoryDict.setCreateUid(adminUserName);
        int insertResp = baseMapper.insert(categoryDict);
        if(insertResp <= 0){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"商品分类表插入失败!");
        }
        return insertResp;
    }

    @Override
    public int updateById(CategoryDictUpdateDto categoryDictUpdateDto) {
        CategoryDict categoryDict = baseMapper.selectById(categoryDictUpdateDto.getId());
        if(categoryDict == null){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"商品分类不存在!");
        }
        BeanUtils.copyProperties(categoryDictUpdateDto,categoryDict);
        int updateResp = baseMapper.updateById(categoryDict);
        if(updateResp <= 0){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"商品分类表更新失败!");
        }
        return updateResp;
    }
}
