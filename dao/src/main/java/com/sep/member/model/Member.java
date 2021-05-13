package com.sep.member.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_member")
@ApiModel(value="Member对象", description=" ")
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;
    @ApiModelProperty(value = "逻辑删除")
    @TableField("is_deleted")
    private Integer isDeleted;
    @ApiModelProperty(value = "申请状态：0：未审核，1：驳回，2：已通过")
    private Integer state;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
