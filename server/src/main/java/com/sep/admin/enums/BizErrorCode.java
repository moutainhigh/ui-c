package com.sep.admin.enums;


import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.ErrorCode;

public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {

    ADMIN_NOT_EXIST(2000,"管理员不存在"),

    PASSWORD_ERROR(2001, "密码错误"),

    ADMIN_IS_EXIST(2002, "登录账号已存在");

    private final int code;
    private final String description;

    BizErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    public static BizErrorCode valueOf(int code) {
        for (BizErrorCode item : BizErrorCode.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }
}

