package com.sep.LiveBroadcast.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class GetLiveListDto {
    @ApiModelProperty("起始拉取房间，start = 0 表示从第 1 个房间开始拉取")
    @NotNull
    private Integer start;
    @ApiModelProperty("每次拉取的个数上限，不要设置过大，建议 100 以内")
    @NotNull
    private Integer limit;
}
