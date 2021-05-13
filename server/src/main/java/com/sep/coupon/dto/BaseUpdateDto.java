package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "基础修改请求对象", description = "基础修改请求对象")
public class BaseUpdateDto {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    @NotEmpty(message = "更新人用户名不能为空")
    @Length(max = 20, message = "更新人用户名长度不能超过20个字符")
    private String updateUid;

}