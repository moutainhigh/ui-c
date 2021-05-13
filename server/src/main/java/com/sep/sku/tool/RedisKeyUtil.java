package com.sep.sku.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 缓存key相关工具
 *
 * @author zhangkai
 * @date 2020年03月24日 00:14
 */
@Component
public class RedisKeyUtil {

    @Value("${cart.prefix}")
    private String cartPrefix;

    public String getCartKey(Integer userId){
        return cartPrefix + ":" + userId;
    }
}
