package com.sep.message.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "SystemMessageStatus", name = "系统消息状态")
public enum SystemMessageStatus implements CommonEnum<SystemMessageStatus> {

    UNPUBLISHED(0, "待发行"),
    PUBLISHED(1, "发行中"),
    SUSPENDED(2, "已撤回");

    private final int code;
    private final String description;

    SystemMessageStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SystemMessageStatus valueOf(int code) {
        for (SystemMessageStatus item : SystemMessageStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}