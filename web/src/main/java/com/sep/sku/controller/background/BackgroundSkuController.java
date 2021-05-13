package com.sep.sku.controller.background;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.message.proxy.SmsAliyuncsProxy;
import com.sep.sku.dto.IdDto;
import com.sep.sku.dto.SaveSkuDto;
import com.sep.sku.dto.SearchSkuDto;
import com.sep.sku.dto.UpdateSkuStatusDto;
import com.sep.sku.service.OrderCodeService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.user.service.AsyncTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/background/sku")
@Api(value = "后台商品相关API", tags = {"后台商品相关API"})
@Slf4j
public class BackgroundSkuController extends BaseController {

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private OrderCodeService orderCodeService;

    @PostMapping(value = "/saveSkuInfo")
    @ApiOperation(value = "保存商品信息", httpMethod = "POST")
    public ResponseData saveSkuInfo(@RequestBody SaveSkuDto saveSkuDto) {
        log.info("【saveSkuInfo】，saveSkuDto:{}", JSON.toJSONString(saveSkuDto));
        int skuId = skuInfoService.saveSkuInfo(saveSkuDto);
        return skuId > 0 ? ResponseData.OK() : ResponseData.ERROR("保存商品信息失败!");
    }

    @PostMapping(value = "/pageSearchSkuInfo")
    @ApiOperation(value = "分页查询商品信息", httpMethod = "POST")
    public ResponseData<IPage<SearchSkuRespVo>> pageSearchSkuInfo(@RequestBody SearchSkuDto searchCardDto) {
        IPage<SearchSkuRespVo> skuInfoPage = skuInfoService.pageSearchSkuInfo(searchCardDto);
        return ResponseData.OK(skuInfoPage);
    }

    @PostMapping(value = "/admissionCodeBySku")
    @ApiOperation(value = "生成入场二维码", httpMethod = "POST")
    public ResponseData<IPage<SearchSkuRespVo>> admissionCodeBySku(@RequestBody IdDto searchCardDto) {
        String admissionCode = orderCodeService.get(searchCardDto);
        if (StringUtils.isEmpty(admissionCode)) {
            admissionCode = asyncTask.admissionCodeBySku(searchCardDto.getId());
            if (StringUtils.isEmpty(admissionCode)){
                return ResponseData.OK(null);
            }
            orderCodeService.add(searchCardDto, admissionCode);
        }
        return ResponseData.OK(admissionCode);
    }

    @PostMapping(value = "/batchUpdateSkuStatus")
    @ApiOperation(value = "批量修改商品状态", httpMethod = "POST")
    public ResponseData updateCardStatus(@RequestBody UpdateSkuStatusDto updateSkuStatusDto) {
        if (updateSkuStatusDto == null) {
            return ResponseData.ERROR("修改商品状态信息为空");
        }
        if (CollectionUtils.isEmpty(updateSkuStatusDto.getIds())) {
            return ResponseData.ERROR("商品id为空");
        }
        int result = skuInfoService.batchUpdateSkuStatus(updateSkuStatusDto);
        return result > 0 ? ResponseData.OK() : ResponseData.ERROR("修改状态失败!");
    }

    @PostMapping(value = "/getSkuInfoById")
    @ApiOperation(value = "查询商品详情", httpMethod = "POST")
    public ResponseData<SearchSkuRespVo> getSkuInfoById(@RequestBody IdDto idDto) {
        if (idDto == null || idDto.getId() == null) {
            return ResponseData.ERROR("id为空");
        }
        return ResponseData.OK(skuInfoService.getSkuInfoById(idDto.getId()));
    }

    @PostMapping(value = "/updateSkuInfo")
    @ApiOperation(value = "修改商品信息", httpMethod = "POST")
    public ResponseData updateCardInfo(@RequestBody SaveSkuDto saveSkuDto) {
        if (saveSkuDto == null) {
            return ResponseData.ERROR("商品信息为空");
        }
        if (saveSkuDto.getId() == null || saveSkuDto.getId().intValue() <= 0) {
            return ResponseData.ERROR("商品id为空或非法");
        }
        boolean result = skuInfoService.updateSkuInfo(saveSkuDto);
        return result ? ResponseData.OK() : ResponseData.ERROR("修改失败！");
    }

    @PostMapping(value = "/deleteSkuInfo")
    @ApiOperation(value = "删除商品信息", httpMethod = "POST")
    public ResponseData<Integer> deleteSkuInfo(@RequestBody IdDto idDto) {
        if (idDto == null || idDto.getId() == null) {
            return ResponseData.ERROR("id为空");
        }
        return ResponseData.OK(skuInfoService.deleteSkuInfo(idDto.getId()));
    }


}
