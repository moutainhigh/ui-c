package com.sep.content.enums;

import com.sep.common.enums.CommonEnum;

public enum ObjType implements CommonEnum<ObjType> {

    ACTIVITY(1, "活动"),
    ARTICLE(2, "资讯"),
    COMMENT(3, "评论"),
    SKU(4, "商品"),
    MEDIA(5,"合作单位");


    private final int code;
    private final String description;

    ObjType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ObjType valueOf(int code) {
        for (ObjType item : ObjType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}