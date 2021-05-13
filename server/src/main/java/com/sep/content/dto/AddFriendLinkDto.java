package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;


@Data
public class AddFriendLinkDto {

    @ApiModelProperty(value = "友情链接ID")
    private Integer id;

    @ApiModelProperty(value = "企业名称")
    @NotBlank(message = "企业名称不能为空")
    private String name;

    @ApiModelProperty(value = "链接地址")
    @NotBlank(message = "链接地址不能为空")
    private String link;

    @ApiModelProperty(value = "排序位置")
    private Integer sort;

    @ApiModelProperty(value = "log图片地址")
    @NotBlank(message = "log图片不能为空")
    private String logo;

}
