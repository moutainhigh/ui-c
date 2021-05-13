package com.sep.content.enums;


import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.ErrorCode;

public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {


    LOGIN_OVERDUE(201, "登录过期"),
    TIME_ERROR(202, "时间错误"),
    NOT_DEL(203, "不可删除"),
    EXISTS(204, "已存在"),
    COMPLETE(205, "已完成，不可修改"),
    NOTNULL_SKU(206, "商品不存在"),
    NOTNILL_ACTIVITY(207, "活动不存在或者已下线"),
    NOTNILL_ARTICLE(208, "资讯不存在或者已下线"),
    SORT_ERROR(209, "排序位置已经存在"),
    ACTIVITYENTER_ERROR(210, "活动报名已满");

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

