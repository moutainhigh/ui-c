package com.sep.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "微信用户收货地址VO", description = "微信用户收货地址VO")
public class WxUserAddressVo {

    @ApiModelProperty(value = "收货地址ID")
    private Integer id;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "地区codeStr")
    private String areaCodeStr;

    @ApiModelProperty(value = "详细地址")
    private String remark;

    @ApiModelProperty(value = "是否默认地址")
    private boolean isAcquiesce;

}