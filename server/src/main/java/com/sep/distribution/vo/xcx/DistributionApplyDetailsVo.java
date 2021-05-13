package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "分销申请详情对象", description = "分销申请详情对象")
public class DistributionApplyDetailsVo {

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    private String distributionIdentityId;

    @ApiModelProperty(value = "身份证正面照")
    private String idCardFrontView;

    @ApiModelProperty(value = "身份证背面照")
    private String idCardBackVision;

    @ApiModelProperty(value = "申请状态")
    private Integer applyState;

    @ApiModelProperty(value = "二维码")
    private String quickMark;

}