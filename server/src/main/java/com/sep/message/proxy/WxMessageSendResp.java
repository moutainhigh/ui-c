package com.sep.message.proxy;

import lombok.Data;

@Data
public class WxMessageSendResp {

    private Integer errcode;
    private String errmsg;

}