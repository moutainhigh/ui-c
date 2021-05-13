package com.sep.point.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "OneDayOperationLimitCode", name = "单日操作量限制设置")
public enum OneDayOperationLimitCode implements CommonEnum<OneDayOperationLimitCode> {

    GIVE_LIKE(3, "单日赞量限制"),
    RETRANSMISSION(4, "单日转发量限制"),
    COMMENT(5, "单日评论量限制"),
    COLLECT(6, "单日收藏量限制");

    public final static int TYPE = 1;

    private final int code;
    private final String description;

    OneDayOperationLimitCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OneDayOperationLimitCode valueOf(int code) {
        for (OneDayOperationLimitCode item : OneDayOperationLimitCode.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}