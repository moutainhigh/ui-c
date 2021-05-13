package com.sep.sku.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sep.sku.model.OrderInformation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInformationVo {
    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "商品名称")
    private String skuName;
    private Integer userId;
    @ApiModelProperty(value = "商品简介")
    private String introduction;
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    @ApiModelProperty(value = "手机号码")
    private List<OrderInformationPhoneVo> phones;
    @ApiModelProperty(value = "数量")
    private Integer size;
    private Integer state;
    private String orderNo;
}
