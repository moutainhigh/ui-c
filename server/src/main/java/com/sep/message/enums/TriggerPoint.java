package com.sep.message.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "TriggerPoint", name = "触发点")
public enum TriggerPoint implements CommonEnum<TriggerPoint> {

    PAY_ORDER(0, "订单待支付"),
    PAY_DONE(1, "支付完成"),
    SHIPMENTS(2, "商家发货"),
    CONFIRM_RECEIPT(3, "确认收货"),
    SUCCESSFUL_COURSE_BOOKING(4, "预约课程成功"),
    CANCELLATION_COURSE_SUCCESSFUL(5, "取消预约成功"),
    THE_CLASS(6, "即将上课"),
    CLASS_BEGINS(7, "课程开始"),
    COURSE_END(8, "课程结束"),
    COURSE_UN_PUBLISHED(9, "课程取消发布");

    private final int code;
    private final String description;

    TriggerPoint(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TriggerPoint valueOf(int code) {
        for (TriggerPoint item : TriggerPoint.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}