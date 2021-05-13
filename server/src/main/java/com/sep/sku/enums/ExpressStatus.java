package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 商品发货状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum ExpressStatus implements CommonEnum<ExpressStatus> {
   WAIT_EXPRESS(0, "待发货"),
   EXPRESSED(1, "已发货"),
   RECEIVED(2, "已收货");

   private final int code;
   private final String description;

    ExpressStatus(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static ExpressStatus valueOf(int code) {
       for (ExpressStatus item : ExpressStatus.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
