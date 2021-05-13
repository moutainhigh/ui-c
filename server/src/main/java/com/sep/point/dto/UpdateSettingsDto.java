package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * <p>
 * 批量修改设置请求对象
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value = "批量修改设置请求对象", description = "批量修改设置请求对象")
public class UpdateSettingsDto {

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    @NotEmpty(message = "更新人用户名不能为空")
    @Length(max = 20, message = "更新人用户名长度不能超过20个字符")
    private String updateUid;

    @ApiModelProperty(value = "设置集合")
    @NotNull(message = "设置集合不能为空")
    @MinSize(value = 1,message = "设置集合不能为空")
    private List<UpdateSettingDto> settings;

}