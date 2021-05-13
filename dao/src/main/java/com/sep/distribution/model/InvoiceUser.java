package com.sep.distribution.model;

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
 * 用户申请表 
 * </p>
 *
 * @author liutianao
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_invoice_user")
@ApiModel(value="InvoiceUser对象", description="用户申请表 ")
public class InvoiceUser extends Model<InvoiceUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "活动id")
    private Integer orderId;

    @ApiModelProperty(value = "乐观锁")
    @TableField("REVISION")
    private Integer revision;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATED_BY")
    private Integer createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATED_BY")
    private Integer updatedBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;
    @ApiModelProperty(value = "备注")
    private String remark1;
    @ApiModelProperty(value = "备注")
    private String remark2;
    @ApiModelProperty(value = "备注")
    private String remark3;
    @ApiModelProperty(value = "备注")
    private String remark4;
    @ApiModelProperty(value = "备注")
    private String remark5;
    @ApiModelProperty(value = "备注")
    private String remark6;
    @ApiModelProperty(value = "备注")
    private String remark7;
    @ApiModelProperty(value = "备注")
    private String remark8;
    @ApiModelProperty(value = "备注")
    private String remark9;
    @ApiModelProperty(value = "备注")
    private String remark10;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
