package com.sep.distribution.dto;

import com.sep.distribution.enums.CashBackWay;
import com.sep.common.oval.MemberOfEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 分销身份修改请求对象
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value = "分销身份修改请求对象", description = "分销身份修改请求对象")
public class IdentityUpdateDto {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    @NotEmpty(message = "更新人用户名不能为空")
    @Length(max = 20, message = "更新人用户名长度不能超过20个字符")
    private String updateUid;

    @ApiModelProperty(value = "身份名称")
    @NotNull(message = "身份名称不能为空")
    @NotEmpty(message = "身份名称不能为空")
    @Length(max = 20, message = "身份名称长度不能超过20个字符")
    private String name;

    @ApiModelProperty(value = "返现方式")
    @NotNull(message = "返现方式不能为空")
    @MemberOfEnum(clazz = CashBackWay.class)
    private Integer cashBackWay;

    @ApiModelProperty(value = "一级返现利息")
    @NotNull(message = "一级返现利息不能为空")
    @Min(value = 0, message = "一级返现利息不能为负")
    private Integer stairInterest;

    @ApiModelProperty(value = "二级返现利息")
    @NotNull(message = "二级返现利息不能为空")
    @Min(value = 0, message = "二级返现利息不能为负")
    private Integer secondLevelInterest;

}