package com.sep.admin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 保存员工信息
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Data
public class SaveEmployeeDto{

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工登录账号")
    private String employeeAccount;

    @ApiModelProperty(value = "员工登录密码")
    private String employeePassword;

    @ApiModelProperty(value = "员工联系方式")
    private String employeePhone;

    @ApiModelProperty(value = "角色id")
    @TableField("employee_roleId")
    private Integer employeeRoleid;

}
