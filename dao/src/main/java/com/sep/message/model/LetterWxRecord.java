package com.sep.message.model;

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
 * 积分表
 * </p>
 *
 * @author litao
 * @since 2020-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_letter_wx_record")
@ApiModel(value = "LetterWxRecord对象", description = "积分表")
public class LetterWxRecord extends Model<LetterWxRecord> {

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

    @ApiModelProperty(value = "触发点")
    private Integer triggerPoint;

    @ApiModelProperty(value = "触发点类型")
    private Integer triggerType;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "模板ID")
    private Integer templateId;

    @ApiModelProperty(value = "信件ID")
    private Integer letterRecordId;

    @ApiModelProperty(value = "发送状态")
    private boolean status;

    @ApiModelProperty(value = "短信发送错误码")
    private Integer wxErrorCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
