package com.sep.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 修改员工信息
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Data
public class UpdateEmployeeDto extends SaveEmployeeDto{

    @ApiModelProperty(value = "员工id")
    @NotNull
    private Integer id;
}
