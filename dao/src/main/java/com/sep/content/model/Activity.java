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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_activity")
@ApiModel(value="Activity对象", description="")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "活动一句话描述")
    private String remark;

    @ApiModelProperty(value = "活动名额")
    private Integer num;

    @ApiModelProperty(value = "当前剩余名额")
    private Integer currentNum;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "活动状态:1未开始，2进行中，3已完成")
    private Integer status;

    @ApiModelProperty(value = "活动地址")
    private String address;

    @ApiModelProperty(value = "地区code")
    private String areaCode;

    @ApiModelProperty(value = "活动主图")
    private String mainImgUrl;

    @ApiModelProperty(value = "活动宣传图主图")
    private String publicityImgUrl;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "转发数量")
    private Integer retransmissionNum;

    @ApiModelProperty(value = "活动内容HTML")
    private String activityContent;

    @ApiModelProperty(value = "关联的商品Id,用  , 分割")
    private String skuIds;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;
    @ApiModelProperty(value = "费用")
    private BigDecimal cost;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
