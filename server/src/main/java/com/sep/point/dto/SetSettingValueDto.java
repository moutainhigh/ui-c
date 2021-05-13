package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
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

    @ApiModelProperty(value = "设置编码")
    @NotNull(message = "设置编码不能为空")
    private Integer code;

    @ApiModelProperty(value = "设置值")
    @NotNull(message = "设置值不能为空")
    @Min(value = 0, message = "积分不能为负数")
    private Integer value;

}