package com.sep.member.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Data
public class MemberBackGroundDto extends PageUtil {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer sex;
}
