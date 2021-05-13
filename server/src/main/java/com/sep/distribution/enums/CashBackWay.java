package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "CashBackWay",name = "返现规则类型")
public enum CashBackWay implements CommonEnum<CashBackWay> {

    FIXATION(0, "固定金额"),
    PERCENT(1, "百分比");

    private final int code;
    private final String description;

    CashBackWay(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CashBackWay valueOf(int code) {
        for (CashBackWay item : CashBackWay.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}