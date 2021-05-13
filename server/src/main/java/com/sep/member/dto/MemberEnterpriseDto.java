package com.sep.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class MemberEnterpriseDto {
    @NotNull
    private String token;
    @ApiModelProperty(value = "id")
    private Integer id;
    @NotNull(message = "单位名称不能为空")
    @ApiModelProperty(value = "单位名称")
    private String name;;
    @NotNull(message = "单位简介不能为空")
    @ApiModelProperty(value = "单位简介")
    private String introduction;;
    @NotNull(message = "单位地址不能为空")
    @ApiModelProperty(value = "单位地址")
    private String address;;
    @NotNull(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String personName;;
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Integer personSex;
    @NotNull(message = "职务不能为空")
    @ApiModelProperty(value = "职务")
    private String personDuties;;
    @NotNull(message = "座机不能为空")
    @ApiModelProperty(value = "座机")
    private String personLandline;;
    @NotNull(message = "手机不能为空")
    @ApiModelProperty(value = "手机")
    private String personPhone;;
    @NotNull(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String personEmail;;
    @NotNull(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String contactsName;

    @ApiModelProperty(value = "申请状态：0：未审核，1：驳回，2：已通过")
    private Integer state;;
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Integer contactsSex;;
    @NotNull(message = "职务不能为空")
    @ApiModelProperty(value = "职务")
    private String contactsDuties;;
    @NotNull(message = "电话不能为空")
    @ApiModelProperty(value = "电话")
    private String contactsLandline;;
    @NotNull(message = "手机不能为空")
    @ApiModelProperty(value = "手机")
    private String contactsPhone;;
    @NotNull(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String contactsEmail;
}
