package com.sep.content.enums;

import com.sep.common.enums.CommonEnum;

public enum CollectType implements CommonEnum<CollectType> {

    SKU(1, "商品"),
    ARTICLE(2, "资讯"),
    MEDIA(3,"合伙人");

    private final int code;
    private final String description;

    CollectType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CollectType valueOf(int code) {
        for (CollectType item : CollectType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}