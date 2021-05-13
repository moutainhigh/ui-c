package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * SKU状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum OrderStatusDesc implements CommonEnum<OrderStatusDesc> {
   WAIT_PAY(0,"待付款"),
//   WAIT_DELIVERY(1,"已付款"),
//   PART_DELIVERY(2,"部分发货"),
//   WAIT_TAKE(3,"待收货"),
    OVER(1,"已完成"),
    CLOSE(2,"交易关闭"),
    REFUNDED(3,"已退款");

   private final int code;
   private final String description;

    OrderStatusDesc(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatusDesc valueOf(int code) {
        for (OrderStatusDesc item : OrderStatusDesc.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
