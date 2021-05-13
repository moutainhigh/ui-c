package com.sep.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工查询条件
 *
 * @author zhangkai
 * @date 2020年02月22日 13:01
 */
@Data
public class SearchEmployeeDto {

    @ApiModelProperty(value = "员工姓名/ID")
    private String employeeName;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;
}
