package com.sep.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "收货地址返回", description = "收货地址返回")
public class AddressVo {


    @ApiModelProperty(value = "收货地址ID")
    private Integer id;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "地区code")
    private String areaCode;


    @ApiModelProperty(value = "地区codeStr")
    private String areaCodeStr;

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "标签：1:家，2公司,3学校")
    private Integer tag;

    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "详细地址")
    private String remark;

    @ApiModelProperty(value = "1 已删除 ，0未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

}
