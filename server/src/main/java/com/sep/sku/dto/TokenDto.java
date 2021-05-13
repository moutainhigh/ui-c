package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 按token查询
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class TokenDto {

    @ApiModelProperty(value = "token")
    private String token;
}
