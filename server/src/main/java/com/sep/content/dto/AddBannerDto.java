package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class AddBannerDto {

    @ApiModelProperty(value = "站点配图Id")
    private Integer id;

    @ApiModelProperty(value = "图片名称")
    @NotNull(message = "请输入图片名称")
    private String name;


    @ApiModelProperty(value = "站点配图类型:1首页banner，2商城banner")
    @NotNull(message = "请选择配图类型")
    private Integer type;

    @ApiModelProperty(value = "排序方式")
    private Integer sort;

    @ApiModelProperty(value = "是否显示：1显示，2不显示")
    @NotNull(message = "请选择配图类型")
    private Integer isShow;

    @ApiModelProperty(value = "图片路径")
    @NotBlank(message = "图片路径不能为空")
    private String imgUrl;

    @ApiModelProperty(value = "关联类型:1活动，2咨询，3商品")
    private Integer objType;

    @ApiModelProperty(value = "活动或者资讯或者商品的id")
    private Integer objId;


}
