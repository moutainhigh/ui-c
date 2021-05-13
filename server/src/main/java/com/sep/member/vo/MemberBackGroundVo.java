package com.sep.member.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberBackGroundVo {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id" )
    private Integer userID;
    @ApiModelProperty(value = "学校公司名称")
    private String corporateName;

    @ApiModelProperty(value = "科系职位")
    private String position;

    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "申请状态：0：未审核，1：驳回，2：已通过")
    private Integer state;
}
