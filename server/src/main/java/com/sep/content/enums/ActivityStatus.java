package com.sep.content.enums;

import com.sep.common.enums.CommonEnum;

public enum ActivityStatus implements CommonEnum<ActivityStatus> {

    Not_Beginning(1, "未开始"),
    ING(2, "进行中"),
    ACCOMPLISH(3, "已完成");

    private final int code;
    private final String description;

    ActivityStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ActivityStatus valueOf(int code) {
        for (ActivityStatus item : ActivityStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}