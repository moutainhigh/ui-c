package com.sep.message.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;
import com.sep.common.enums.ErrorCode;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@EnumName(id = "BizErrorCode", name = "错误码")
public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {

    SYSTEM_MESSAGE_SAVE_ERROR(1200, "系统消息保存失败"),
    SYSTEM_MESSAGE_UPDATE_ERROR(1201, "系统消息更新失败"),
    SYSTEM_MESSAGE_PUBLISHED_ERROR(1203, "系统消息发布失败"),
    SYSTEM_MESSAGE_SUSPENDED_ERROR(1204, "系统消息撤回失败"),
    SYSTEM_MESSAGE_DELETE_ERROR(1205, "系统消息删除失败"),
    SYSTEM_MESSAGE_READ_ERROR(1206, "系统消息查看失败"),
    LETTER_TEMPLATE_ENABLE_ERROR(1207, "信件模板启用失败"),
    LETTER_TEMPLATE_DISABLE_ERROR(1208, "信件模板关闭失败"),
    LETTER_READ_ERROR(1209, "信件查看失败"),
    SYSTEM_MESSAGE_PUBLISHED_STATUS_ERROR(1210, "含有非待发布数据，请重新选择发布"),
    SYSTEM_MESSAGE_SUSPENDED_STATUS_ERROR(1211, "含有非待撤回数据，请重新选择撤回"),
    SEND_LETTER_ERROR(1212, "信件发送失败"),
    LETTER_TEMPLATE_NOT_FOUND_ERROR(1213, "信件模板不存在或者是已经关闭"),
    LETTER_TEMPLATE_PROCESS_ERROR(1214, "信件模板解析错误");

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