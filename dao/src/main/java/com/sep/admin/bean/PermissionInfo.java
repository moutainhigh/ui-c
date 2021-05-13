package com.sep.admin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限信息
 *
 * @author zhangkai
 * @date 2020年02月22日 16:00
 */
@Data
public class PermissionInfo {

    @ApiModelProperty("权限id")
    private int id;

    @ApiModelProperty("权限名称")
    private String name;

}
