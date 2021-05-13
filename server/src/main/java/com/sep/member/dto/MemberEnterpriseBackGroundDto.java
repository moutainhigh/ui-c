package com.sep.member.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberEnterpriseBackGroundDto extends PageUtil {
    @ApiModelProperty(value = "单位名称")
    private String name;
    @ApiModelProperty(value = "负责人姓名")
    private String personName;
    @ApiModelProperty(value = "联系人名称")
    private String contactsName;
}
