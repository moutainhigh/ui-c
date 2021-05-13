package com.sep.sku.controller.server;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sep.common.model.response.ResponseData;
import com.sep.sku.dto.BatchSearchSkuInfoDto;
import com.sep.sku.dto.IdDto;
import com.sep.sku.enums.RecommendType;
import com.sep.sku.enums.SkuStatus;
import com.sep.sku.model.CategoryDict;
import com.sep.sku.vo.SimpleSkuInfoVo;
import com.sep.sku.vo.SkuInfoRespVo;
import com.sep.sku.dto.SearchRecommendSkuDto;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务端 商品 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@Slf4j
@RequestMapping("/server/sku")
@Api(value = "服务端商品相关API", tags = {"服务端商品相关API"})
public class ServerSkuController extends BaseController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 查询商品信息
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "查询商品信息", httpMethod = "POST")
    List<SkuInfoRespVo> getSkuListByIds(@RequestBody BatchSearchSkuInfoDto batchSearchSkuInfoDto) {
        return skuInfoService.getSkuListByIds(batchSearchSkuInfoDto);
    }

    /**
     * 商品是否上线状态
     *
     * @param skuId
     * @return
     */
    @PostMapping(value = "isOnline")
    @ApiOperation(value = "商品是否上线状态", httpMethod = "POST")
    boolean isSkuOnlineStatus(@RequestBody Integer skuId) {
        return skuInfoService.isSkuOnlineStatus(skuId);
    }

    /**
     * 获取上线的热门精选商品
     *
     * @return
     */
    @PostMapping(value = "hotSkuList")
    @ApiOperation(value = "获取上线的热门精选商品", httpMethod = "POST")
    List<SkuInfoRespVo> hotSkuList() {
        return skuInfoService.hotSkuList();
    }



    @GetMapping(value = "/getSkuByOnPutaway")
    @ApiOperation(value = "获取平台全部上线的商品", httpMethod = "GET")
    public ResponseData<List<SimpleSkuInfoVo>> getSkuByOnPutaway() {
        return ResponseData.OK(skuInfoService.getSkuByOnPutaway());
    }
}
