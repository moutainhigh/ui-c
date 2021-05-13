package com.sep.content.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FriendLinkVo {

    @ApiModelProperty(value = "友情链接ID")
    private Integer id;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "链接地址")
    private String link;

    @ApiModelProperty(value = "排序位置")
    private Integer sort;

    @ApiModelProperty(value = "log图片地址")
    private String logo;


}
