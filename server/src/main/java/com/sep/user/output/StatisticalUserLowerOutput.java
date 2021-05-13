package com.sep.user.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户下级统计信息", description = "用户下级统计信息")
@Data
public class StatisticalUserLowerOutput {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号码")
    private String telnum;

    @ApiModelProperty("下级数量")
    private Integer lower1Count;

    @ApiModelProperty("下下级数量")
    private Integer lower2Count;

    @ApiModelProperty(value = "邀请人ID（谁邀请的我）")
    private Integer inviteParentId;

    @ApiModelProperty(value = "二维码")
    private String quickMark;

}