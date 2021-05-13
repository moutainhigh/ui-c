package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 支付方式枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum PayWayEnum implements CommonEnum<PayWayEnum> {
   WEIXIN_PAY(0, "微信"),
   INTEGRAL_PAY(1, "积分");

   private final int code;
   private final String description;

    PayWayEnum(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static PayWayEnum valueOf(int code) {
       for (PayWayEnum item : PayWayEnum.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
