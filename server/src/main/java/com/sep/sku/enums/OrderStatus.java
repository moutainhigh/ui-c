package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * SKU状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum OrderStatus implements CommonEnum<OrderStatus> {
   WAIT_PAY(0, "待支付"),
//   PAYED(1, "已支付"),
   CANCELED(2, "已取消"),
   REFUNDED(3,"已退款"),
   OVER(1,"已完成");

   private final int code;
   private final String description;

    OrderStatus(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static OrderStatus valueOf(int code) {
       for (OrderStatus item : OrderStatus.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
