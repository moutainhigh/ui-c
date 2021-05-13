package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class SearchColleDto extends PageUtil {

    @ApiModelProperty(value = "当前登录用户")
    @NotBlank
    private String token;

    @ApiModelProperty(value = "收藏类型：1商品，2资讯")
    @NotNull
    private Integer objType;


}
