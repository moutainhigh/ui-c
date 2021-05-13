package com.sep.sku.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品返回信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class SkuInfoRespVo{

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "商品主图url,多个,隔开")
    private String skuPictureUrl;

    @ApiModelProperty(value = "商品视频url")
    private String skuVideoUrl;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品库存，0：不设库存")
    private Integer stockNum;

    @ApiModelProperty(value = "实际销量")
    private Integer saleNum;

    @ApiModelProperty(value = "库存扣减方式，0：付款减库存")
    private Boolean deductionType;

    @ApiModelProperty(value = "支持的配送方式，如1,2")
    private String distributionType;

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人用户名")
    private String createUid;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "商品首张图片")
    private String skuFirstPictureUrl;


}
