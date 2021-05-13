package com.sep.sku.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tianyu
 * @since 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_facilitator")
@ApiModel(value="Facilitator对象", description="")
public class Facilitator extends Model<Facilitator> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商表主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务商名称")
    private String facilitatorName;

    @ApiModelProperty(value = "服务商负责人")
    private String leadingOfficial;

    @ApiModelProperty(value = "服务商电话")
    private String phoneNum;

    @ApiModelProperty(value = "淘宝店铺名")
    private String taobaoName;

    @ApiModelProperty(value = "微信账号")
    private String wxId;

    @ApiModelProperty(value = "逻辑删除:1已删除,0未删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
