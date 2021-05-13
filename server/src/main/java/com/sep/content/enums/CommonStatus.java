package com.sep.content.enums;

import com.sep.common.enums.CommonEnum;

public enum CommonStatus implements CommonEnum<CommonStatus> {

    YES(1, "YES"),
    NO(-1, "NO");

    private final int code;
    private final String description;

    CommonStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CommonStatus valueOf(int code) {
        for (CommonStatus item : CommonStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}