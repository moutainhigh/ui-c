package com.sep.sku.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "快递信息描述", description = "快递信息描述")
public class SearchOrderLogisticsRespVo {

    @ApiModelProperty(value = "快递公司名称")
    private String expressName;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "商品信息")
    private List<SearchSkuRespVo> skuInfoList;

}