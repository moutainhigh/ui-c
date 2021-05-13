package com.sep.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

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
@TableName("tb_member_enterprise")
@ApiModel(value = "MemberEnterprise对象", description = " ")
public class MemberEnterprise extends Model<MemberEnterprise> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
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
    @ApiModelProperty(value = "用户id")
    @TableField("user_id" )
    private Integer userID;
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
