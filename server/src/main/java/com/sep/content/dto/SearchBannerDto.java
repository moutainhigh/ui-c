package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchBannerDto extends PageUtil {

    @ApiModelProperty(value = "站点配图类型:1首页banner，2商城banner")
    private Integer type;
}
