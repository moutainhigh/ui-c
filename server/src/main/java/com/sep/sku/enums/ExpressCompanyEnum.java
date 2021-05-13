package com.sep.sku.enums;


import com.sep.common.enums.CommonEnum;

/**
 * 快递公司枚举类
 *
 * @author zhangkai
 * @date 2019/12/23 下午11:45
 */
public enum ExpressCompanyEnum implements CommonEnum<ExpressCompanyEnum> {
   SHUNFENG_SUYUN(1, "顺丰速运"),
   YOUZHENG_KUAIDI(2, "邮政快递"),
   ZHAI_JI_SONG(3, "宅急送"),
   ZHONGTONG_KUAIDI(4, "中通快递"),
   YUANTONG_KUAIDI(5, "圆通速递"),
   SHENTONG_KUAIDI(6, "申通快递"),
   BAISHI_KUAIDI(7, "百世快递"),
   YUNDA_KUAIDI(8, "韵达快递"),
   TIANTIAN_KUAIDI(9, "天天快递"),
   JINGDONG_KUAIDI(10, "京东快递");

   private final int code;
   private final String description;

    ExpressCompanyEnum(int code, String description) {
       this.code = code;
       this.description = description;
   }

   public int getCode() {
       return code;
   }

   public String getDescription() {
       return description;
   }

   public static ExpressCompanyEnum valueOf(int code) {
       for (ExpressCompanyEnum item : ExpressCompanyEnum.values()) {
           if (item.getCode() == code) {
               return item;
           }
       }
       throw new IllegalArgumentException("未知的");
   }

}
