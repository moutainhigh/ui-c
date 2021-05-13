package com.sep.user.model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_area")
@ApiModel(value="Area对象", description="")
public class Area extends Model<Area> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer orders;

    private String fullname;

    private String name;

    private String treepath;

    private Long parent;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createdate;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime modifydate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
