package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * SKU状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum SkuStatus implements CommonEnum<SkuStatus> {
    WAIT_PUTAWAY(0, "待上架"),
    ON_PUTAWAY(1, "上架中"),
    SELL_OUT(2, "已售罄"),
    SOLD_OUT(3, "已下架"),
    CLOSURE(4, "已结束");


    private final int code;
    private final String description;

    SkuStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SkuStatus valueOf(int code) {
        for (SkuStatus item : SkuStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
