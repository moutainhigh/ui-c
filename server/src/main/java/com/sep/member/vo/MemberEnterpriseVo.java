package com.sep.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class MemberEnterpriseVo {
    private Integer id;
    @ApiModelProperty(value = "单位名称")
    private String name;
    @ApiModelProperty(value = "单位简介")
    private String introduction;
    @ApiModelProperty(value = "单位地址")
    private String address;
    @ApiModelProperty(value = "姓名")
    private String personName;
    @ApiModelProperty(value = "性别")
    private Integer personSex;
    @ApiModelProperty(value = "职务")
    private String personDuties;
    @ApiModelProperty(value = "座机")
    private String personLandline;
    @ApiModelProperty(value = "手机")
    private String personPhone;
    @ApiModelProperty(value = "邮箱")
    private String personEmail;
    @ApiModelProperty(value = "名称")
    private String contactsName;
    @ApiModelProperty(value = "性别")
    private Integer contactsSex;
    @ApiModelProperty(value = "职务")
    private String contactsDuties;
    @ApiModelProperty(value = "座机")
    private String contactsLandline;
    @ApiModelProperty(value = "手机")
    private String contactsPhone;
    @ApiModelProperty(value = "邮箱")
    private String contactsEmail;

    @ApiModelProperty(value = "申请状态：0：未审核，1：驳回，2：已通过")
    private Integer state;
}
