package com.sep.distribution.dto;

import com.sep.distribution.enums.FundChangeType;
import com.sep.common.oval.MemberOfEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDate;

@Data
@ApiModel(value = "资金变动分页查询请求对象", description = "资金变动分页查询请求对象")
public class SearchFundChangeDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "资金变动类型")
    @MemberOfEnum(clazz = FundChangeType.class)
    private Integer fundChangeType;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private LocalDate startEnd;

    @ApiModelProperty(value = "当前页")
    @NotNull(message = "当前页不能为空")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    @NotNull(message = "每页长度不能为空")
    private Long pageSize = 10L;

}