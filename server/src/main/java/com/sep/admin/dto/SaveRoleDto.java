package com.sep.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 保存角色信息
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Data
@ApiModel(value="保存角色信息", description="保存角色信息")
public class SaveRoleDto{

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "权限ids集合，,隔开")
    private List<Integer> permissionIdList;

}
