package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "分销申请请求对象", description = "分销申请请求对象")
public class DistributionApplyDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    @NotNull(message = "真实姓名不能为空")
    @NotEmpty(message = "真实姓名不能为空")
    @Length(max = 20, message = "真实姓名长度不能超过20个字符")
    private String name;

    @ApiModelProperty(value = "身份证号")
    @NotNull(message = "身份证号不能为空")
    @NotEmpty(message = "身份证号不能为空")
    @Length(min = 15, max = 18, message = "身份证号错误")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    @NotNull(message = "分销身份不能为空")
    private Integer distributionIdentityId;

    @ApiModelProperty(value = "身份证正面照")
    @NotNull(message = "身份证正面照不能为空")
    @NotEmpty(message = "身份证正面照不能为空")
    @Length(max = 255, message = "身份证正面照长度不能超过255个字符")
    private String idCardFrontView;

    @ApiModelProperty(value = "身份证背面照")
    @NotNull(message = "身份证背面照不能为空")
    @NotEmpty(message = "身份证背面照不能为空")
    @Length(max = 255, message = "身份证背面照长度不能超过255个字符")
    private String idCardBackVision;

}