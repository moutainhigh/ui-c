package com.sep.message.proxy;

import lombok.Data;

@Data
public class AuthTokenResp {

    private String access_token;
    private long expires_in;
    private String errcode;
    private String errmsg;

}