package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 设置值请求对象
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value = "设置值请求对象", description = "设置值请求对象")
public class SetSettingValueDto {

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    @NotEmpty(message = "更新人用户名不能为空")
    @Length(max = 20, message = "更新人用户名长度不能超过20个字符")
    private String updateUid;

    @ApiModelProperty(value = "设置类型")
    @NotNull(message = "设置类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "设置名称")
    @NotNull(message = "设置名称不能为空")
    @NotEmpty(message = "设置名称不能为空")
    @Length(max = 20, message = "设置名称长度不能超过20个字符")
    private String name;

    @ApiModelProperty(value = "设置值")
    @NotNull(message = "设置值不能为空")
    @NotEmpty(message = "设置值不能为空")
    @Length(max = 20, message = "设置值长度不能超过20个字符")
    private String value;

}