package com.sep.distribution.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;
import com.sep.common.enums.ErrorCode;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "BizErrorCode", name = "错误码")
public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {

    DISTRIBUTION_APPLY_ERROR(801, "分销申请提交失败"),
    WITHDRAW_APPLY_ERROR(802, "提现申请提交失败"),
    WITHDRAW_AVAILABLE_LACK(803, "提现金额超过了可提现金额"),
    CASHBACK_ERROR(804, "返现失败"),
    IDENTITY_ADD_ERROR(805, "分销身份保存失败"),
    IDENTITY_UPDATE_ERROR(806, "分销身份保存失败"),
    IDENTITY_ENABLE_ERROR(807, "分销身份启用失败"),
    IDENTITY_DISABLE_ERROR(808, "分销身份停用失败"),
    APPLY_APPROVE_ERROR(809, "分销申请通过失败"),
    APPLY_REJECT_ERROR(810, "分销申请驳回失败"),
    WITHDRAW_APPROVE_ERROR(811, "返现申请通过失败"),
    WITHDRAW_REJECT_ERROR(812, "返现申请驳回失败"),
    SETTING_ERROR(813, "设置失败"),
    MINWITHDRAW_ERROR(814, "提现金额不能小于最小提现金额%s"),
    IDENTITY_NAME_EXIST(815, "分销角色名已存在");

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