package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * SKU配送方式
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum DistributionType implements CommonEnum<DistributionType> {
   OWN_DELIVERY(1, "自提"),
   EXPRESSAGE(2, "快递");

   private final int code;
   private final String description;

    DistributionType(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static DistributionType valueOf(int code) {
       for (DistributionType item : DistributionType.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       return null;
   }

}
