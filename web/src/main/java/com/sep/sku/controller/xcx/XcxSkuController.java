package com.sep.sku.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.sku.dto.*;
import com.sep.sku.model.CategoryDict;
import com.sep.sku.service.CategoryDictService;
import com.sep.sku.service.OrderInformationService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.OrderInformationVo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/xcx/sku")
@Api(value = "小程序商品相关API", tags = {"小程序商品相关API"})
@Slf4j
public class XcxSkuController extends BaseController{

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CategoryDictService categoryDictService;

    @Autowired
    private OrderInformationService orderInformationService;

    @PostMapping(value= "/getSkuListByRecommendType")
    @ApiOperation(value = "根据推荐类型获取商品集合",httpMethod = "POST")
    public ResponseData<List<SearchSkuRespVo>> getSkuListByRecommendType(@RequestBody SearchRecommendSkuDto searchRecommendSkuDto){
        List<SearchSkuRespVo> skuRespVo = skuInfoService.getSkuListByRecommendType(searchRecommendSkuDto);
        return ResponseData.OK(skuRespVo);
    }

    @PostMapping(value = "/getSkuCategoryList")
    @ApiOperation(value = "查询商品分类集合", httpMethod = "POST")
    public ResponseData<List<CategoryDict>> getSkuCategoryList(){
        List<CategoryDict> categoryDictList = categoryDictService.getAllSkuCategory();
        return ResponseData.OK(categoryDictList);
    }

    @PostMapping(value = "/getSkuCategoryListForXcx")
    @ApiOperation(value = "小程序查看分类集合", httpMethod = "POST")
    public ResponseData<List<CategoryDict>> getSkuCategoryListForXcx() {
        List<CategoryDict> categoryDictList = categoryDictService.getSkuCategoryForXcx();
        return ResponseData.OK(categoryDictList);
    }

    @PostMapping(value= "/pageSearchSkuList")
    @ApiOperation(value = "分页查询商品信息",httpMethod = "POST")
    public ResponseData<IPage<SearchSkuRespVo>> pageSearchSkuList(@RequestBody SearchSkuDto searchSkuDto){
        IPage<SearchSkuRespVo> skuRespVoPage = skuInfoService.pageSearchSkuInfoByXcx(searchSkuDto);
        return ResponseData.OK(skuRespVoPage);
    }

    @PostMapping(value= "/getSkuInfoById")
    @ApiOperation(value = "查询商品详情",httpMethod = "POST")
    public ResponseData<SearchSkuRespVo> getSkuInfoById(@RequestBody SkuDetailDto skuDetailDto){
        return ResponseData.OK(skuInfoService.getSkuInfoBySkuDetailDto(skuDetailDto));
    }

    @PostMapping(value= "/generateShareQrCode")
    @ApiOperation(value = "生成分享二维码",httpMethod = "POST")
    public ResponseData<String> generateShareQrCode(@RequestBody GenerateShareQrCodeDto generateShareQrCodeDto){
        return ResponseData.OK(skuInfoService.generateShareQrCode(generateShareQrCodeDto));
    }
}
