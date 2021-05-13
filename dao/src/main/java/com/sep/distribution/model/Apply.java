package com.sep.distribution.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 分销申请表
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_distribution_apply")
@ApiModel(value = "Apply对象", description = "分销申请表")
public class Apply extends Model<Apply> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间 ")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人用户名")
    private String createUid;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    private Integer distributionIdentityId;

    @ApiModelProperty(value = "身份证正面照")
    private String idCardFrontView;

    @ApiModelProperty(value = "身份证背面照")
    private String idCardBackVision;

    @ApiModelProperty(value = "申请状态")
    private Integer applyState;

    @ApiModelProperty(value = "二维码")
    private String quickMark;

    @ApiModelProperty(value = "邀请人ID（谁邀请的我）")
    private Integer inviteParentId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
