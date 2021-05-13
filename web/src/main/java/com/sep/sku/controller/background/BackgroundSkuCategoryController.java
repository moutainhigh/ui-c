package com.sep.sku.controller.background;


import com.sep.sku.dto.CategoryDictSaveDto;
import com.sep.sku.dto.CategoryDictUpdateDto;
import com.sep.sku.dto.IdDto;
import com.sep.sku.model.CategoryDict;
import com.sep.sku.service.CategoryDictService;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品分类字典表 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/background/sku_category")
@Api(value = "后台商品分类相关API", tags = {"后台商品分类相关API"})
public class BackgroundSkuCategoryController extends BaseController{

    @Autowired
    private CategoryDictService categoryDictService;

    @PostMapping(value= "/getAllSkuCategory")
    @ApiOperation(value = "查询所有商品分类信息",httpMethod = "POST")
    public ResponseData<CategoryDict> getAllSkuCategory(){
        return ResponseData.OK(categoryDictService.getAllSkuCategory());
    }

    @PostMapping(value= "/save")
    @ApiOperation(value = "添加商品分类",httpMethod = "POST")
    public ResponseData saveSkuCategory(@RequestBody CategoryDictSaveDto categoryDictSaveDto){
        return ResponseData.OK(categoryDictService.saveSkuCategory(categoryDictSaveDto));
    }

    @PostMapping(value= "/update")
    @ApiOperation(value = "修改商品分类",httpMethod = "POST")
    public ResponseData updateSkuCategory(@RequestBody CategoryDictUpdateDto categoryDictUpdateDto){
        return ResponseData.OK(categoryDictService.updateById(categoryDictUpdateDto));
    }

    @PostMapping(value= "/searchById")
    @ApiOperation(value = "查看商品分类详情",httpMethod = "POST")
    public ResponseData<CategoryDict> searchById(@RequestBody IdDto idDto){
        return ResponseData.OK(categoryDictService.getById(idDto.getId()));
    }

    @PostMapping(value= "/deleteById")
    @ApiOperation(value = "删除商品分类详情",httpMethod = "POST")
    public ResponseData deleteById(@RequestBody IdDto idDto){
        return ResponseData.OK(categoryDictService.removeById(idDto.getId()));
    }

}
