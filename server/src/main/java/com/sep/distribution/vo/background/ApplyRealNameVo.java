package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="分销者实名信息对象", description="分销者实名信息对象")
public class ApplyRealNameVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "身份证正面照")
    private String idCardFrontView;

    @ApiModelProperty(value = "身份证背面照")
    private String idCardBackVision;

}