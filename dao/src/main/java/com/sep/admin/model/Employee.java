package com.sep.admin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工信息表
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_employee")
@ApiModel(value="Employee对象", description="员工信息表")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工登录账号")
    private String employeeAccount;

    @ApiModelProperty(value = "员工登录密码")
    private String employeePassword;

    @ApiModelProperty(value = "员工编号")
    private Integer employeeNo;

    @ApiModelProperty(value = "员工联系方式")
    private String employeePhone;

    @ApiModelProperty(value = "角色id")
    @TableField("employee_roleId")
    private Integer employeeRoleid;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人用户名")
    private String createUid;

    @ApiModelProperty(value = "更新人用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
