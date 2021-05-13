package com.sep.distribution.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class IdentityDto {

    @ApiModelProperty(value = "主键")

    private Integer id;

    @ApiModelProperty(value = "创建时间 ")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人用户名")
    private String createUid;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "身份名称")
    private String name;

    @ApiModelProperty(value = "返现方式")
    private Integer cashBackWay;

    @ApiModelProperty(value = "一级返现利息")
    private Integer stairInterest;

    @ApiModelProperty(value = "二级返现利息")
    private Integer secondLevelInterest;

    @ApiModelProperty(value = "是否启用")
    private Integer enable;

    @ApiModelProperty(value = "有效期开始日期")
    private LocalDate validityStartDate;

    @ApiModelProperty(value = "有效期结束日期")
    private LocalDate validityEndDate;

}
