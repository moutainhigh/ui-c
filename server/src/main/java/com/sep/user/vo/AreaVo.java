package com.sep.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "地域信息", description = "地域信息")
public class AreaVo {

    @ApiModelProperty(value = "地域Id")
    private Long id;

    @ApiModelProperty(value = "地域名称")
    private String name;

    @ApiModelProperty(value = "地域父级Id")
    private Long parent;

    public AreaVo(Long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }
}
