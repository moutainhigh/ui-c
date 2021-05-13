package com.sep.sku.dto;

import com.sep.sku.bean.SkuParamInfo;
import com.sep.sku.bean.SkuPropertySaveInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Sku基本信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value = "添加Sku信息", description = "添加Sku信息")
public class SaveSkuDto {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "门店id")
    private Integer shopId;

    @ApiModelProperty(value = "商品所属分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "商品简介")
    private String introduction;

    @ApiModelProperty(value = "单位(个、台)")
    private String skuUnit;

    @ApiModelProperty(value = "排序")
    private Integer serial;

    @ApiModelProperty(value = "售卖基数，0：不设基数")
    private Integer saleBase;

    @ApiModelProperty(value = "商品视频url")
    private String skuVideoUrl;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品库存，0：不设库存")
    private Integer stockNum;

    @ApiModelProperty(value = "库存扣减方式，0：付款减库存")
    private Boolean deductionType;

    @ApiModelProperty(value = "快递费")
    private BigDecimal expressMoney;

    @ApiModelProperty(value = "是否显示图文详情，0：不显示，1：显示")
    private Boolean isShowDesc;

    @ApiModelProperty(value = "图文详情")
    private String skuDesc;

    @ApiModelProperty(value = "是否显示参数，0：不显示，1：显示")
    private Boolean isShowParam;

    @ApiModelProperty(value = "参数介绍")
    private String paramDesc;

    @ApiModelProperty(value = "是否显示规格，0：不显示，1：显示")
    private Boolean isShowSpec;

    @ApiModelProperty(value = "规则介绍")
    private String specDesc;

    @ApiModelProperty(value = "是否显示包装清单，0：不显示，1：显示")
    private Boolean isShowPackage;

    @ApiModelProperty(value = "包装清单介绍")
    private String packageDesc;

    @ApiModelProperty(value = "是否显示售后服务")
    private Boolean isShowService;

    @ApiModelProperty(value = "售后服务介绍")
    private String serviceDesc;

    @ApiModelProperty(value = "是否是推荐商品，0：否，1：是")
    private Boolean isRecommend;

    @ApiModelProperty(value = "是否推荐至首页商品，0：否，1：是")
    private Boolean isRecommendHome;

    @ApiModelProperty(value = "是否推荐至热销商品，0：否，1：是")
    private Boolean isRecommendHot;

    @ApiModelProperty(value = "是否推荐至主打商品，0：否，1：是")
    private Boolean isRecommendSide;

    @ApiModelProperty(value = "商品状态，0：待上架，1：已上架，2：已售罄，3：已下架")
    private int status;

    @ApiModelProperty(value = "是否支持积分兑换，0：不支持，1：支持")
    private Boolean isIntegralExchange;

    @ApiModelProperty(value = "积分兑换数量")
    private int integralNum;

    @ApiModelProperty(value = "是否参与分销，0：不参与，1：参与")
    private Boolean isPartDistribution;




    @ApiModelProperty(value = "服务商ID")
    private Integer facilitatoId;

    @ApiModelProperty(value = "商品结束时间")
    private String skuEndTime;

    @ApiModelProperty(value = "是否为热门商品:0不是，1是")
    private Integer hotSku;

    @ApiModelProperty(value = "代金或者折扣描述")
    private String rebateDepict;

    @ApiModelProperty(value = "有效期")
    private String periodTime;

    @ApiModelProperty(value = "商品列表图")
    private String skuListImg;

    @ApiModelProperty(value = "商品属性保存集合")
    private List<SkuPropertySaveInfo> skuPropertySaveInfoList;

    @ApiModelProperty(value = "商品参数集合")
    private List<SkuParamInfo> skuParamInfoList;

    @ApiModelProperty(value = "商品主图url集合")
    private List<String> skuPictureUrlList;

    @ApiModelProperty(value = "支持的配送方式集合,1:自提，2:配送")
    private List<Integer> distributionTypeIdList;
}
