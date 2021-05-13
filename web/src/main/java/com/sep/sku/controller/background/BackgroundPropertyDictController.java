package com.sep.sku.controller.background;


import com.sep.sku.bean.SkuPropertyInfo;
import com.sep.sku.dto.SearchPropertyDictDto;
import com.sep.sku.service.PropertyDictService;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/background/property_dict")
@Api(value = "后台商品属性字典相关API", tags = {"后台商品属性字典相关API"})
public class BackgroundPropertyDictController extends BaseController{

    @Autowired
    private PropertyDictService propertyDictService;

//    @PostMapping(value= "/getAllSkuProperty")
//    @ApiOperation(value = "查询所有商品属性字典信息",httpMethod = "POST")
//    public ResponseData<List<SkuPropertyInfo>> getAllPropertyDictInfo(SearchPropertyDictDto searchPropertyDictDto){
//        return ResponseData.OK(propertyDictService.getAllPropertyDictInfo(searchPropertyDictDto));
//    }

}
