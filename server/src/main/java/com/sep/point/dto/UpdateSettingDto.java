package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 更新设置请求对象
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value = "更新设置请求对象", description = "更新设置请求对象")
public class UpdateSettingDto {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "设置值")
    @NotNull(message = "设置值不能为空")
    @Min(value = 0, message = "积分不能为负数")
    private Integer value;

}