package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;


@Data
@ApiModel(value = "添加或修改收货地址", description = "添加或修改收货地址")
public class AddOrUpdateAddressDto {

    @ApiModelProperty(value = "收货地址ID")
    private Integer id;

    @ApiModelProperty(value = "用户token")
    @NotNull(message = "token不能为空")
    @NotEmpty(message = "token不能为空")
    private String token;

    @ApiModelProperty(value = "地区code")
    @NotNull(message = "地区不能为空")
    @NotEmpty(message = "地区不能为空")
    private String areaCode;

    @ApiModelProperty(value = "收货人姓名")
    @NotEmpty(message = "收货人姓名不能为空")
    @NotNull(message = "收货人姓名不能为空")
    private String name;

    @ApiModelProperty(value = "标签：1:家，2公司,3学校")
    private Integer tag;

    @ApiModelProperty(value = "收货人手机号")
    @NotNull(message = "手机号不能为空")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "详细地址")
    @NotEmpty(message = "详细地址不能为空")
    @NotNull(message = "详细地址不能为空")
    private String remark;


}
