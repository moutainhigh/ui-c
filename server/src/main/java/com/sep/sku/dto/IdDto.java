package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


/**
 * ID查询
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class IdDto {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Integer id;
}
