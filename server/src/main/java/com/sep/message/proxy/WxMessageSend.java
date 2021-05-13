package com.sep.message.proxy;

import lombok.Data;

@Data
public class WxMessageSend {

    private String touser;
    private String template_id;
    private String page;
    private Object data;

}