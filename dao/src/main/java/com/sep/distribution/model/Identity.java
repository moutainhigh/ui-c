package com.sep.distribution.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 分销身份表
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_distribution_identity")
@ApiModel(value="Identity对象", description="分销身份表")
public class Identity extends Model<Identity> {

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

    @TableLogic
    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "身份名称")
    private String name;

    @ApiModelProperty(value = "返现方式")
    private Integer cashBackWay;

    @ApiModelProperty(value = "一级返现利息")
    private Integer stairInterest;

    @ApiModelProperty(value = "二级返现利息")
    private Integer secondLevelInterest;

    @ApiModelProperty(value = "是否启用")
    private Integer enable;

    @ApiModelProperty(value = "有效期开始日期")
    private LocalDate validityStartDate;

    @ApiModelProperty(value = "有效期结束日期")
    private LocalDate validityEndDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
