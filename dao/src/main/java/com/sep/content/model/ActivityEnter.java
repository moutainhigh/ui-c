package com.sep.content.model;

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
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author tianyu
 * @since 2020-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_activity_enter")
@ApiModel(value = "ActivityEnter对象", description = "")
public class ActivityEnter extends Model<ActivityEnter> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动报名Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "活动Id")
    private Integer activityId;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "报名人姓名")
    private String name;

    @ApiModelProperty(value = "报名人 手机号")
    private String phone;

    @ApiModelProperty(value = "报名人年龄")
    private Integer age;

    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
    private Integer isApply;

    @ApiModelProperty(value = "逻辑删除:1已删除,0未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
