package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "DistributionRank",name = "返现等级")
public enum DistributionRank implements CommonEnum<DistributionRank> {

    STAIR(0, "一级"),
    SECOND_LEVEL(1, "二级");

    private final int code;
    private final String description;

    DistributionRank(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static DistributionRank valueOf(int code) {
        for (DistributionRank item : DistributionRank.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}