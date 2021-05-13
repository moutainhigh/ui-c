package com.sep.sku.controller.xcx;


import com.sep.sku.dto.*;
import com.sep.sku.service.CartService;
import com.sep.sku.service.SettlementService;
import com.sep.sku.service.ValuationService;
import com.sep.sku.vo.*;
import com.sep.common.controller.BaseController;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 小程序端 购物车 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/xcx/cart")
@Api(value = "小程序购物车相关API", tags = {"小程序购物车相关API"})
@Slf4j
public class XcxCartController extends BaseController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private ValuationService valuationService;

//    @PostMapping(value = "/list")
//    @ApiOperation(value = "查询购物车", httpMethod = "POST")
//    public ResponseData<List<CartSkuVo>> searchCartList(@RequestBody TokenDto tokenDto) {
//        int userId = (int) JwtUtils.parseJWT(tokenDto.getToken()).get("id");
//        return ResponseData.OK(cartService.searchCartList(userId));
//    }
//
//    @PostMapping(value = "/add")
//    @ApiOperation(value = "添加购物车", httpMethod = "POST")
//    public ResponseData<List<CartSkuVo>> addToCart(@RequestBody SettlementQuickBuySkuDto settlementQuickBuySkuDto) {
//        return ResponseData.OK(cartService.addToCart(settlementQuickBuySkuDto));
//    }
//
//    @PostMapping(value = "/updateSkuCount")
//    @ApiOperation(value = "修改购物车产品数量", httpMethod = "POST")
//    public ResponseData updateSkuCount(@RequestBody UpdateCartDto updateCartDto) {
//        cartService.updateSkuCount(updateCartDto);
//        return ResponseData.OK();
//    }
//
//    @PostMapping(value = "/batchDeleteSku")
//    @ApiOperation(value = "批量删除购物车产品", httpMethod = "POST")
//    public ResponseData batchDeleteSku(@RequestBody BatchDeleteSkuCartDto batchDeleteSkuCartDto) {
//        cartService.batchDeleteSku(batchDeleteSkuCartDto);
//        return ResponseData.OK();
//    }
//
//    @PostMapping(value = "/clean")
//    @ApiOperation(value = "清空购物车", httpMethod = "POST")
//    public ResponseData clean(@RequestBody TokenDto tokenDto) {
//        int userId = (int) JwtUtils.parseJWT(tokenDto.getToken()).get("id");
//        cartService.clean(userId);
//        return ResponseData.OK();
//    }
//
//    @PostMapping(value = "/cartToQuickSettlement")
//    @ApiOperation(value = "购物车一键结算", httpMethod = "POST")
//    public ResponseData<SettlementIndexRespVo> cartToBuySettlement(@RequestBody TokenDto tokenDto) {
//
//        // 获取购物车所有商品
//        int userId = (int) JwtUtils.parseJWT(tokenDto.getToken()).get("id");
//        List<CartSkuVo> cartSkuVoList = cartService.searchCartList(userId);
//        if (CollectionUtils.isEmpty(cartSkuVoList)) {
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "购物车为空");
//        }
//        List<SettlementSkuDto> settlementSkuDtoList = convertSkuDtoToSettDtoList(cartSkuVoList);
//        // 进入结算页
//        SettlementIndexRespVo settlementIndexRespVo = settlementService.skuSettlement(userId, settlementSkuDtoList);
//        return ResponseData.OK(settlementIndexRespVo);
//    }
//
//    @PostMapping(value = "/cartToSettlement")
//    @ApiOperation(value = "购物车批量结算", httpMethod = "POST")
//    public ResponseData<SettlementIndexRespVo> cartToBuySettlement(@RequestBody CartBatchToSettlementDto cartBatchToSettlementDto) {
//
//        // 获取购物车所有商品
//        int userId = (int) JwtUtils.parseJWT(cartBatchToSettlementDto.getToken()).get("id");
//        List<CartSkuVo> cartSkuVoList = cartService.searchCartList(userId);
//        if (CollectionUtils.isEmpty(cartSkuVoList)) {
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "购物车为空");
//        }
//        List<String> currentSettSkuList = cartBatchToSettlementDto.getSkuUniqueKeyList();
//        List<CartSkuVo> filterCartSkuVoList = cartSkuVoList.stream().filter(new Predicate<CartSkuVo>() {
//            @Override
//            public boolean test(CartSkuVo cartSkuVo) {
//                return currentSettSkuList.contains(cartSkuVo.getSkuUniqueKey());
//            }
//        }).collect(Collectors.toList());
//
//        if (CollectionUtils.isEmpty(filterCartSkuVoList)) {
//            log.error("【cartToSettlement】,filterCartSkuVoList is empty");
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品有误");
//        }
//        if (filterCartSkuVoList.size() != currentSettSkuList.size()) {
//            log.error("【cartToSettlement】,cartSkuVoList count is error,filterCartSkuVoList:{},current_count:{}", filterCartSkuVoList.size(), currentSettSkuList.size());
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品有误");
//        }
//        List<SettlementSkuDto> settlementSkuDtoList = convertSkuDtoToSettDtoList(filterCartSkuVoList);
//        // 进入结算页
//        SettlementIndexRespVo settlementIndexRespVo = settlementService.skuSettlement(userId, settlementSkuDtoList);
//        return ResponseData.OK(settlementIndexRespVo);
//    }
//
//    @PostMapping(value = "/cartValuation")
//    @ApiOperation(value = "购物车计价", httpMethod = "POST")
//    public ResponseData<CartValuationRespVo> cartValuation(@RequestBody CartBatchToSettlementDto cartBatchToSettlementDto) {
//        if (CollectionUtils.isEmpty(cartBatchToSettlementDto.getSkuUniqueKeyList())) {
//            return ResponseData.OK(new CartValuationRespVo());
//        }
//        // 获取购物车所有商品
//        int userId = (int) JwtUtils.parseJWT(cartBatchToSettlementDto.getToken()).get("id");
//        List<CartSkuVo> cartSkuVoList = cartService.searchCartList(userId);
//        if (CollectionUtils.isEmpty(cartSkuVoList)) {
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "购物车为空");
//        }
//        List<String> currentSettSkuList = cartBatchToSettlementDto.getSkuUniqueKeyList();
//        List<CartSkuVo> filterCartSkuVoList = cartSkuVoList.stream().filter(new Predicate<CartSkuVo>() {
//            @Override
//            public boolean test(CartSkuVo cartSkuVo) {
//                return currentSettSkuList.contains(cartSkuVo.getSkuUniqueKey());
//            }
//        }).collect(Collectors.toList());
//
//        if (CollectionUtils.isEmpty(filterCartSkuVoList)) {
//            log.error("【cartValuation】,filterCartSkuVoList is empty");
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品有误");
//        }
//        if (filterCartSkuVoList.size() != currentSettSkuList.size()) {
//            log.error("【cartValuation】,cartSkuVoList count is error,filterCartSkuVoList:{},current_count:{}", filterCartSkuVoList.size(), currentSettSkuList.size());
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品有误");
//        }
//        List<SettlementSkuDto> settlementSkuDtoList = convertSkuDtoToSettDtoList(filterCartSkuVoList);
//        CartValuationRespVo cartValuationRespVo = valuationService.cartValuation(settlementSkuDtoList);
//        return ResponseData.OK(cartValuationRespVo);
//
//    }
//
//    private List<SettlementSkuDto> convertSkuDtoToSettDtoList(List<CartSkuVo> cartSkuVoList) {
//        List<SettlementSkuDto> settlementSkuDtoList = cartSkuVoList.stream().map(cartSkuVo -> {
//            SettlementSkuDto settlementSkuDto = new SettlementSkuDto();
//            BeanUtils.copyProperties(cartSkuVo, settlementSkuDto);
////            settlementSkuDto.setSkuPropertyInfoList(cartSkuVo.getSettlementPropertyInfoList());
//            return settlementSkuDto;
//        }).collect(Collectors.toList());
//        return settlementSkuDtoList;
//    }
}
