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
 * 站点配图表
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_banner")
@ApiModel(value="Banner对象", description="站点配图表")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点配图Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "图片名称")
    private String  name;

    @ApiModelProperty(value = "站点配图类型:1首页banner，2商城banner")
    private Integer type;

    @ApiModelProperty(value = "排序方式")
    private Integer sort;

    @ApiModelProperty(value = "是否显示：1显示，2不显示")
    private Integer isShow;

    @ApiModelProperty(value = "图片路径")
    private String imgUrl;

    @ApiModelProperty(value = "关联类型:1活动，2咨询，3商品")
    private Integer objType;

    @ApiModelProperty(value = "活动或者资讯或者商品的id")
    private Integer objId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
