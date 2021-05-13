package com.sep.admin.vo;


import com.sep.admin.model.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchEmployeeRespVo extends Employee{

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
