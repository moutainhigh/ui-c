package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "WithdrawAccountType",name = "提现申请状态")
public enum WithdrawStatus implements CommonEnum<WithdrawStatus> {

    WAITCONFIRM(0, "待审核"),
    APPROVE(1, "通过"),
    REJECT(2, "驳回");

    private final int code;
    private final String description;

    WithdrawStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static WithdrawStatus valueOf(int code) {
        for (WithdrawStatus item : WithdrawStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}