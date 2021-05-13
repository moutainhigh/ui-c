package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 商品自提状态枚举
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum FetchStatus implements CommonEnum<FetchStatus> {
   WAIT_FETCH(0, "待自提"),
   FETCHED(1, "已自提");

   private final int code;
   private final String description;

    FetchStatus(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static FetchStatus valueOf(int code) {
       for (FetchStatus item : FetchStatus.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
