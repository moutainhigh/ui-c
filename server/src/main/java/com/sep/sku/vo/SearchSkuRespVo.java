package com.sep.sku.vo;

import com.sep.sku.bean.SkuParamInfo;
import com.sep.sku.bean.SkuPropertyInfo;
import com.sep.sku.model.SkuInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO
 *
 * @author zhangkai
 * @date 2019年12月28日 23:34
 */
@Data
public class SearchSkuRespVo extends SkuInfo {

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "商品主图url集合")
    private List<String> skuPictureUrlList;

    @ApiModelProperty(value = "商品首张主图url")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "支持的配送方式名称集合")
    private List<String> distributionTypeList;

    @ApiModelProperty(value = "支持的配送方式id集合,1:自提，2:配送")
    private List<Integer> distributionTypeIdList;

    @ApiModelProperty(value = "商品属性集合")
    private List<SkuPropertyInfo> skuPropertyInfoList;

    @ApiModelProperty(value = "商品参数集合")
    private List<SkuParamInfo> skuParamInfoList;

    @ApiModelProperty(value = "是否只允许积分兑换")
    private Boolean isOnlyIntegralExchange;

    @ApiModelProperty(value = "积分是否足够")
    private Boolean isIntegralEnough;

    @ApiModelProperty(value = "商品结束时间毫秒数")
    private Long millis;

    @ApiModelProperty(value = "商品类型")
    private Integer skuType;

    @ApiModelProperty(value = "服务商名称")
    private String facilitatName;

    @ApiModelProperty(value = "收藏数量")
    private Integer collectNum;

//    @ApiModelProperty(value = "折到位优惠卷")
//    private String encryptParam;



}
