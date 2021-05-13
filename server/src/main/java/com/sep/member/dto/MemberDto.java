package com.sep.member.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Integer id;
    @NotNull
    private String token;
    @NotNull
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotNull
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "学校公司名称")
    private String corporateName;

    @ApiModelProperty(value = "科系职位")
    private String position;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "申请状态：0：未审核，1：驳回，2：已通过")
    private Integer state;
}
