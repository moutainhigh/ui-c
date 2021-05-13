package com.sep.distribution.vo.background;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceUserVo {
    @ApiModelProperty(value = "活动id")
    private Integer orderId;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;
    @ApiModelProperty(value = "备注")
    private String remark1;
    @ApiModelProperty(value = "备注")
    private String remark2;
    @ApiModelProperty(value = "备注")
    private String remark3;
    @ApiModelProperty(value = "备注")
    private String remark4;
    @ApiModelProperty(value = "备注")
    private String remark5;
    @ApiModelProperty(value = "备注")
    private String remark6;
    @ApiModelProperty(value = "备注")
    private String remark7;
    @ApiModelProperty(value = "备注")
    private String remark8;
    @ApiModelProperty(value = "备注")
    private String remark9;
    @ApiModelProperty(value = "备注")
    private String remark10;
}
