package com.sep.message.dto;

import com.sep.message.enums.LetterNotifyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "我的信件分页查询请求对象", description = "我的信件分页查询请求对象")
public class MyLetterDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "通知类型")
    private Integer triggerType = LetterNotifyType.ORDER.getCode();

    @ApiModelProperty(value = "当前页")
    @NotNull(message = "当前页不能为空")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    @NotNull(message = "每页长度不能为空")
    private Long pageSize = 10L;

}