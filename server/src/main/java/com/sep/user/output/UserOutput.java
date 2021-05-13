package com.sep.user.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOutput {


    private static final long serialVersionUID = 1L;

    private Integer id;

    private String openid;

    private String unionid;

    private String sessionKey;

    private String nickname;

    private String avatarurl;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String language;

    private String telnum;

    private Integer inviteParentId;

    private String inviteQrCode;

    private Integer isAuthUserinfo;

    private LocalDateTime createTime;

    private LocalDateTime ts;

    private Integer lower1Count;

}
