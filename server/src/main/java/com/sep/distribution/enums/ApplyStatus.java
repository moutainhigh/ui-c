package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "ApplyStatus",name = "分销申请状态")
public enum ApplyStatus implements CommonEnum<ApplyStatus> {

    WAITCONFIRM(0, "待审核"),
    APPROVE(1, "通过"),
    REJECT(2, "驳回");

    private final int code;
    private final String description;

    ApplyStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ApplyStatus valueOf(int code) {
        for (ApplyStatus item : ApplyStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}