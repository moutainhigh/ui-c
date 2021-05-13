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
 * 订单sku属性信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_sku_property")
@ApiModel(value="OrderSkuProperty对象", description="订单sku属性信息")
public class OrderSkuProperty extends Model<OrderSkuProperty> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单skuId，对应tb_order_sku表id")
    private Integer orderSkuId;

    @ApiModelProperty(value = "sku属性id，对应tb_sku_property表id")
    private Integer skuPropertyId;

    @ApiModelProperty(value = "sku属性值字典表id，自定义属性为空")
    private Integer propertyValueDictId;

    @ApiModelProperty(value = "sku属性值名称，如红色")
    private String propertyValue;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
