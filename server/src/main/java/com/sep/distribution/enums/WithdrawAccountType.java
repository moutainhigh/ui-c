package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "WithdrawAccountType",name = "提现帐号类型")
public enum WithdrawAccountType implements CommonEnum<WithdrawAccountType> {

    WAITCONFIRM(0, "微信"),
    APPROVE(1, "支付宝"),
    BANK_CARD(2, "银行卡");

    private final int code;
    private final String description;

    WithdrawAccountType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static WithdrawAccountType valueOf(int code) {
        for (WithdrawAccountType item : WithdrawAccountType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}