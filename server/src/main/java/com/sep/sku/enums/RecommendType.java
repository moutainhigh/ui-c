package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 商品推荐类型
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum RecommendType implements CommonEnum<RecommendType> {
   HOME_RECOMMEND(1, "首页推荐"),
   HOT_SALE_RECOMMEND(2, "热销推荐"),
   SIDE_SHOW_RECOMMEND(3, "主打推荐");

   private final int code;
   private final String description;

    RecommendType(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static RecommendType valueOf(int code) {
       for (RecommendType item : RecommendType.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
