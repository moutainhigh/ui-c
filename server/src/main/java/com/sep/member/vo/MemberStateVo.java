package com.sep.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberStateVo {
    @ApiModelProperty("是否是会员：0：不是，1：个人会员，2：企业会员")
    private Integer state;
}
