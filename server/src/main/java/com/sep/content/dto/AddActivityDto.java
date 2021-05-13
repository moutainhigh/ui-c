package com.sep.content.dto;


import com.sep.content.model.ActivitySignUp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.util.List;


@Data
public class AddActivityDto {


    @ApiModelProperty(value = "活动Id")
    private Integer id;

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "活动一句话描述")
    private String remark;

    @ApiModelProperty(value = "活动名额")
    @NotNull(message = "活动名额不能为空")
    private Integer num;

    @ApiModelProperty(value = "活动开始时间")
    @NotBlank(message = "开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "活动结束时间")
    @NotBlank(message = "活动结束时间不能为空")
    private String endTime;


    @ApiModelProperty(value = "活动地址")
    private String address;

    @ApiModelProperty(value = "地区code")
    private String areaCode;

    @ApiModelProperty(value = "活动主图")
    private String mainImgUrl;

    @ApiModelProperty(value = "活动宣传图主图")
    private String publicityImgUrl;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "转发数量")
    private Integer retransmissionNum;

    @ApiModelProperty(value = "活动内容HTML")
    private String activityContent;
    @ApiModelProperty(value = "费用")
    private BigDecimal cost;

    @ApiModelProperty(value = "关联的商品Id,用  , 分割")
    private String skuIds;
    @ApiModelProperty(value = "用户填写项")
    private List<ActivitySignUpDto> activitySignUp;
}
