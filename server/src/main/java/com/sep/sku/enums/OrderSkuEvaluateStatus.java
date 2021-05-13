package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 订单商品状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum OrderSkuEvaluateStatus implements CommonEnum<OrderSkuEvaluateStatus> {

    STAY(0, "待评价"),
    OVER(1, "已评价");

    private final int code;
    private final String description;

    OrderSkuEvaluateStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderSkuEvaluateStatus valueOf(int code) {
        for (OrderSkuEvaluateStatus item : OrderSkuEvaluateStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
