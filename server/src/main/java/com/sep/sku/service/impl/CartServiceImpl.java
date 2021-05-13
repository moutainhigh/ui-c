package com.sep.sku.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sep.sku.bean.SettlementPropertyInfo;
import com.sep.sku.dto.BatchDeleteSkuCartDto;
import com.sep.sku.dto.SettlementQuickBuySkuDto;
import com.sep.sku.dto.UpdateCartDto;
import com.sep.sku.enums.SkuStatus;
import com.sep.sku.service.CartService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.tool.RedisKeyUtil;
import com.sep.sku.vo.CartSkuVo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RedisKeyUtil redisKeyUtil;


    @Override
    public List<CartSkuVo> searchCartList(Integer userId) {
        if (userId == null) {
            return Lists.newArrayList();
        }
        //从redis中根据userId作为key去取集合
        //这里使用序列化器Jackson2JsonRedisSerializer让redisTemplate支持序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CartSkuVo.class));
        //根据刚才保存的map键名为test，得到其保存的值
        List<Object> cartList = redisTemplate.boundHashOps(getCartRedisUserKey(userId)).values();
        if (cartList == null) {
            return Lists.newArrayList();
        }
        List<CartSkuVo> list = cartList.stream().map(o -> {
            CartSkuVo cartSkuVo = (CartSkuVo) o;
            return cartSkuVo;
        }).collect(Collectors.toList());

        List<CartSkuVo> result = new ArrayList<>();
        list.forEach(e -> {
            SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(e.getSkuId());
            if (skuRespVo.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
                result.add(e);
            }
        });
        putSkuVoListToCache(userId, result);
        return result;
    }

    @Override
    public String generatorSkuUniqueKey(int skuId, List<SettlementPropertyInfo> propertyInfoList) {
        StringBuilder sb = new StringBuilder();
        sb.append(skuId);
        if (!CollectionUtils.isEmpty(propertyInfoList)) {
            // 按skuPropertyId排序，这样能保证集合的有序性
            propertyInfoList.sort(Comparator.comparing(SettlementPropertyInfo::getSkuPropertyId));
            // 生成购物车商品唯一key
            for (SettlementPropertyInfo propertyInfo : propertyInfoList) {
                sb.append("|").append(propertyInfo.getSkuPropertyId() + ":" + propertyInfo.getPropertyValue());
            }
        }
        return sb.toString();
    }

    @Override
    public List<CartSkuVo> addToCart(SettlementQuickBuySkuDto settlementQuickBuySkuDto) {
        log.info("[addToCart],settlementQuickBuySkuDto is {}", JSON.toJSONString(settlementQuickBuySkuDto));
        SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(settlementQuickBuySkuDto.getSkuId());
        if (skuRespVo == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品不存在");
        }
        if (skuRespVo.getStatus() != SkuStatus.ON_PUTAWAY.getCode()) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品已下架或者已结束");
        }
        // 生成购物车商品唯一key
//        String currentSkuUniqueKey = generatorSkuUniqueKey(settlementQuickBuySkuDto.getSkuId(),
//                settlementQuickBuySkuDto.getSkuPropertyInfoList());
        // 取出购物车的商品，如果有此商品并且属性全部一致，购物车此商品数量+1，如果不一致，新增一个商品信息
        int userId = (int) JwtUtils.parseJWT(settlementQuickBuySkuDto.getToken()).get("id");
        List<CartSkuVo> cartSkuVoList = searchCartList(userId);
        boolean isHaveRepeatSku = false;
        if (!CollectionUtils.isEmpty(cartSkuVoList)) {
            for (CartSkuVo cartSkuVo : cartSkuVoList) {
//                if (cartSkuVo.getSkuUniqueKey().equals(currentSkuUniqueKey)) {
//                    cartSkuVo.setBuyNum(cartSkuVo.getBuyNum() + settlementQuickBuySkuDto.getBuyNum());
//                    isHaveRepeatSku = true;
//                    break;
//                }
            }
        }
        if (isHaveRepeatSku) {
            // 重新放入缓存
            putSkuVoListToCache(userId, cartSkuVoList);
            return cartSkuVoList;
        }

        // 购物车中新增一个产品
        CartSkuVo newCartSkuVo = new CartSkuVo();
        BeanUtils.copyProperties(settlementQuickBuySkuDto, newCartSkuVo);
        BeanUtils.copyProperties(skuRespVo, newCartSkuVo);
//        newCartSkuVo.setSkuUniqueKey(currentSkuUniqueKey);
//        newCartSkuVo.setSettlementPropertyInfoList(settlementQuickBuySkuDto.getSkuPropertyInfoList());
        cartSkuVoList.add(newCartSkuVo);
        // 重新放入缓存
        putSkuVoListToCache(userId, cartSkuVoList);
        return cartSkuVoList;
    }

    @Override
    public void updateSkuCount(UpdateCartDto updateCartDto) {
        log.info("[updateSkuCount],updateCartDto is {}", JSON.toJSONString(updateCartDto));
        int userId = (int) JwtUtils.parseJWT(updateCartDto.getToken()).get("id");
        log.info("[updateSkuCount],userId is {}", userId);
        List<CartSkuVo> cartSkuVoList = searchCartList(userId);
        log.info("[updateSkuCount],cartSkuVoList is {}", JSON.toJSONString(cartSkuVoList));
        if (CollectionUtils.isEmpty(cartSkuVoList)) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "购物车为空");
        }
        boolean isHaveCurrentSku = false;
        for (CartSkuVo cartSkuVo : cartSkuVoList) {
            if (cartSkuVo.getSkuUniqueKey().equals(updateCartDto.getSkuUniqueKey())) {
                // 修改商品数量
                cartSkuVo.setBuyNum(updateCartDto.getBuyNum());
                isHaveCurrentSku = true;
                break;
            }
        }
        if (!isHaveCurrentSku) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品不存在");
        }
        // 重新放入缓存
        putSkuVoListToCache(userId, cartSkuVoList);
    }

    @Override
    public void batchDeleteSku(BatchDeleteSkuCartDto batchDeleteSkuCartDto) {
        log.info("[batchDeleteSku],batchDeleteSkuCartDto is {}", JSON.toJSONString(batchDeleteSkuCartDto));
        if (CollectionUtils.isEmpty(batchDeleteSkuCartDto.getSkuUniqueKeyList())) {
            log.warn("[batchDeleteSku],deleteSkuList is empty");
            return;
        }
        int userId = (int) JwtUtils.parseJWT(batchDeleteSkuCartDto.getToken()).get("id");
        List<CartSkuVo> cartSkuVoList = searchCartList(userId);
        if (CollectionUtils.isEmpty(cartSkuVoList)) {
            log.warn("[batchDeleteSku],cartSkuVoList is empty");
            return;
        }
        List<String> deleteCartSkuVoKeyList = cartSkuVoList.stream().filter(new Predicate<CartSkuVo>() {
            @Override
            public boolean test(CartSkuVo cartSkuVo) {
                return batchDeleteSkuCartDto.getSkuUniqueKeyList().contains(cartSkuVo.getSkuUniqueKey());
            }
        }).collect(Collectors.toList()).stream().map(cartSkuVo -> {
            return cartSkuVo.getSkuUniqueKey();
        }).collect(Collectors.toList());
        // 删除缓存购物车
        redisTemplate.boundHashOps(getCartRedisUserKey(userId)).delete(deleteCartSkuVoKeyList.toArray());

    }

    @Override
    public void clean(Integer userId) {
        redisTemplate.delete(getCartRedisUserKey(userId));
    }

    private void putSkuVoListToCache(int userId, List<CartSkuVo> cartSkuVoList) {
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CartSkuVo.class));
        //为hash设置存储类型(map标志键名，散列key，散列value)
        for (CartSkuVo cartSkuVo : cartSkuVoList) {
            redisTemplate.boundHashOps(getCartRedisUserKey(userId)).put(cartSkuVo.getSkuUniqueKey(), cartSkuVo);
        }
    }

    private String getCartRedisUserKey(Integer userId) {
        return redisKeyUtil.getCartKey(userId);
    }
}
