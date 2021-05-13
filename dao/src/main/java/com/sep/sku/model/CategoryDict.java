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

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品分类字典表
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_category_dict")
@ApiModel(value="CategoryDict对象", description="商品分类字典表")
public class CategoryDict extends Model<CategoryDict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "序号")
    private Integer serial;

    @ApiModelProperty(value = "是否显示，0：否，1：是")
    private int isDisplay;

    @ApiModelProperty(value = "是否推荐，0：否，1：是")
    private int isRecommend;

    @ApiModelProperty(value = "分类图标url")
    private String categoryIcon;

    @ApiModelProperty(value = "分类描述")
    private String categoryDesc;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建用户名")
    private String createUid;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Boolean isDeleted;

    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
