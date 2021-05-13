package com.sep.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 修改角色信息
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Data
@ApiModel(value="修改角色信息", description="修改角色信息")
public class UpdateRoleDto extends SaveRoleDto{

    @ApiModelProperty(value = "角色id")
    @NotNull
    private Integer id;

}
