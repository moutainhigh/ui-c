package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="分销申请详情对象", description="分销申请详情对象")
public class ApplyPageSearchVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    private String distributionIdentity;

    @ApiModelProperty(value = "身份证正面照")
    private String idCardFrontView;

    @ApiModelProperty(value = "身份证背面照")
    private String idCardBackVision;

    @ApiModelProperty(value = "申请状态")
    private Integer applyState;

}