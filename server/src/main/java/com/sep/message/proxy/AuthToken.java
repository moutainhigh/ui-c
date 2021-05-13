package com.sep.message.proxy;

import lombok.Data;

@Data
public class AuthToken {

    private volatile String access_token;

    private static final AuthToken INSTANCE = new AuthToken();

    public static AuthToken getInstance() {
        return INSTANCE;
    }

}