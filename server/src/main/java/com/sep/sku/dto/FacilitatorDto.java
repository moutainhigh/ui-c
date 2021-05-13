package com.sep.sku.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;

import java.time.LocalDateTime;

@Data
public class FacilitatorDto {

    @ApiModelProperty(value = "服务商表主键")
    private Integer id;

    @ApiModelProperty(value = "服务商名称")
    @NotBlank(message = "服务商名称不能为空")
    private String facilitatorName;

    @ApiModelProperty(value = "服务商负责人")
    @NotBlank(message = "服务商负责人不能为空")
    private String leadingOfficial;

    @ApiModelProperty(value = "服务商电话")
    @NotBlank(message = "服务商电话不能为空")
    private String phoneNum;

    @ApiModelProperty(value = "淘宝店铺名")
    private String taobaoName;

    @ApiModelProperty(value = "微信账号")
    private String wxId;


}
