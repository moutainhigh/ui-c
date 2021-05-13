package com.sep.admin.vo;


import com.sep.admin.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchRoleRespVo extends Role{

    @ApiModelProperty(value = "员工数量")
    private int employeeCount;

    @ApiModelProperty(value = "权限id集合")
    private List<Integer> permissionIdList;

}
