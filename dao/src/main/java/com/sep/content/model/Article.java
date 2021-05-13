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
 *  资讯表
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_article")
@ApiModel(value="Article对象", description=" 资讯表")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章Id(新闻资讯)")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "分类Id")
    private Integer articleClassifyId;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "宣传图主图")
    private String publicityImgUrl;

    @ApiModelProperty(value = "封面图片URL")
    private String surfaceImgUrl;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "是否推荐：1推荐，-1不推荐")
    private Integer isRecommend;

    @ApiModelProperty(value = "推荐时间")
    private LocalDateTime recommendTime;

    @ApiModelProperty(value = "文章内容HTML")
    private String articleContent;

    @ApiModelProperty(value = "关联的商品Id,用  ,  分割")
    private String skuIds;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "转发数")
    private Integer retransmissionNum;

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
