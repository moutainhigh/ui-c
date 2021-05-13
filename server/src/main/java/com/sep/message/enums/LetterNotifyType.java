package com.sep.message.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "LetterNotifyType", name = "信件通知类型")
public enum LetterNotifyType implements CommonEnum<LetterNotifyType> {

    ORDER(0, "订单通知"),
    COURSE(1, "课程通知");

    private final int code;
    private final String description;

    LetterNotifyType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static LetterNotifyType valueOf(int code) {
        for (LetterNotifyType item : LetterNotifyType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}