package com.sep.user.model;

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
 *
 * </p>
 *
 * @author tianyu
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_wx_user")
@ApiModel(value = "WxUser对象", description = "")
public class WxUser extends Model<WxUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小程序用户openid")
    private String openid;

    @ApiModelProperty(value = "小程序用户unionid")
    private String unionid;

    @ApiModelProperty(value = "小程序用户session_key")
    private String sessionKey;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatarurl;

    @ApiModelProperty(value = "性别   0 男  1  女  2 人妖")
    private Integer gender;

    @ApiModelProperty(value = "所在国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    private String language;

    @ApiModelProperty(value = "手机号码")
    private String telnum;

    @ApiModelProperty(value = "邀请人ID（谁邀请的我）")
    private Integer inviteParentId;

    @ApiModelProperty(value = "本人的邀请二维码")
    private String inviteQrCode;

    @ApiModelProperty(value = "是否授权用户信息")
    private Integer isAuthUserinfo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
