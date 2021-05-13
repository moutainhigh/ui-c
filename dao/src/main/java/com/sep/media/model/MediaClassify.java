package com.sep.media.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章分类表 
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_media_classify")
@ApiModel(value="MediaClassify对象", description="文章分类表 ")
public class MediaClassify extends Model<MediaClassify> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合伙人分类Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "分类图标")
    private String imgUrl;

    @ApiModelProperty(value = "分类排序")
    private Integer sort;

    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "文章数量")
    private Integer mediaNum;

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
