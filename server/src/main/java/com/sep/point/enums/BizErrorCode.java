package com.sep.point.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.ErrorCode;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {

    POINT_SETTING_NOT_FOUND_ERROR(1100, "积分设置不存在"),
    ONE_DAY_OPERATION_LIMIT_ERROR(1101, "每日增加积分次数超限"),
    SETTING_ERROR(1102, "积分设置失败"),
    POINT_CHANGE_ERROR(1103, "积分变动失败"),
    POINT_LACK_ERROR(1104, "积分不足");

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