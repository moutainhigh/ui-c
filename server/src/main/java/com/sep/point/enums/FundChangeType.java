package com.sep.point.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "FundChangeType", name = "资金变动类型")
public enum FundChangeType implements CommonEnum<FundChangeType> {

    FIRST_AUTH(0, "初次授权"),
    DAILY_LOGIN(1, "每日登录"),
    INVITE_FRIENDS(2, "邀请好友"),
    GIVE_LIKE(3, "赞"),
    RETRANSMISSION(4, "转发"),
    COMMENT(5, "评论"),
    COLLECT(6, "收藏"),
    DEALS_DONE(7, "交易成功"),
    ACTIVITY_REGISTRATION(8, "活动报名"),
    PRODUCTS_EXCHANGE(9, "兑换商品");

    private final int code;
    private final String description;

    FundChangeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static FundChangeType valueOf(int code) {
        for (FundChangeType item : FundChangeType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}