package com.sep.admin.enums;

import com.sep.common.enums.CommonEnum;

/**
  * 权限枚举
  *
  * @author zhangkai
  * @date 2019/12/23 下午11:45
  */
public enum PermissionEnum implements CommonEnum<PermissionEnum> {
    SETUP(1, "设置"),
    USER(2, "用户"),
    CONTENT(3, "内容"),
    SKU(4, "商品"),
    ORDER(5, "订单"),
    MONEY(6, "资金"),
    MARKETING(7, "营销");

    private final int code;
    private final String description;

     PermissionEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PermissionEnum valueOf(int code) {
        for (PermissionEnum item : PermissionEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
