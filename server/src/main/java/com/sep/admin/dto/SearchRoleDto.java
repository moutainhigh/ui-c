package com.sep.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色查询条件
 *
 * @author zhangkai
 * @date 2020年02月22日 13:01
 */
@Data
public class SearchRoleDto {

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;
}
