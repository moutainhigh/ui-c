package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.ErrorCode;

public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {


    SKU_IS_ONLINE(1101, "上架中的商品不能删除");

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

