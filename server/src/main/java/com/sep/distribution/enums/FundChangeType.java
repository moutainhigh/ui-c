package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "FundChangeType",name = "资金变动类型")
public enum FundChangeType implements CommonEnum<FundChangeType> {

    STAIR_DISTRIBUTION(0, "一级返现"),
    SECOND_LEVEL_DISTRIBUTION(1, "二级返现"),
    WITHDRAW(2,"提现");

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