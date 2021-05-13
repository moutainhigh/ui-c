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
public class SimpleSkuInfoVo {


    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "商品主图url,多个,隔开")
    private String skuPictureUrl;

    @ApiModelProperty(value = "商品视频url")
    private String skuVideoUrl;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品首张图片")
    private String skuFirstPictureUrl;


}
