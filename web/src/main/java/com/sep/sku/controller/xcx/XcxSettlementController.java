package com.sep.sku.controller.xcx;


import com.google.common.collect.Lists;
import com.sep.common.exceptions.SepCustomException;
import com.sep.sku.dto.CouponValuationDto;
import com.sep.sku.dto.SettlementQuickBuySkuDto;
import com.sep.sku.dto.SettlementSkuDto;
import com.sep.sku.service.CartService;
import com.sep.sku.service.SettlementService;
import com.sep.sku.vo.CouponValuationRespVo;
import com.sep.sku.vo.SettlementIndexRespVo;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 小程序端 结算 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/xcx/settlement")
@Api(value = "小程序结算相关API", tags = {"小程序结算相关API"})
public class XcxSettlementController extends BaseController {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private CartService cartService;


    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[0,5-9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\\\d{8}$\";";

    @PostMapping(value = "/quickBuySettlement")
    @ApiOperation(value = "立即购买确认订单", httpMethod = "POST")
    public ResponseData<SettlementIndexRespVo> quickBuySettlement(@RequestBody SettlementQuickBuySkuDto settlementQuickBuySkuDto) {
        if (settlementQuickBuySkuDto.getPhones() == null || settlementQuickBuySkuDto.getPhones().size() != settlementQuickBuySkuDto.getBuyNum()) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写全部手机号码");
        }
        settlementQuickBuySkuDto.getPhones().forEach(e -> {
            if (getPhone(e) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的手机号码");
            }
        });
        int userId = (int) JwtUtils.parseJWT(settlementQuickBuySkuDto.getToken()).get("id");
//        String skuUniqueKey = cartService.generatorSkuUniqueKey(
//                settlementQuickBuySkuDto.getSkuId(),settlementQuickBuySkuDto.getSkuPropertyInfoList());
        SettlementSkuDto settlementSkuDto = new SettlementSkuDto();
        BeanUtils.copyProperties(settlementQuickBuySkuDto, settlementSkuDto);
//        settlementSkuDto.setSkuUniqueKey(skuUniqueKey);
        SettlementIndexRespVo settlementIndexRespVo = settlementService.skuSettlement(userId, Lists.newArrayList(settlementSkuDto));
        settlementIndexRespVo.setPhones(settlementQuickBuySkuDto.getPhones());
        return ResponseData.OK(settlementIndexRespVo);
    }

    //    @PostMapping(value= "/couponValuation")
//    @ApiOperation(value = "优惠券计价",httpMethod = "POST")
//    public ResponseData<CouponValuationRespVo> quickBuySettlement(@RequestBody CouponValuationDto couponValuationDto){
//        CouponValuationRespVo couponValuationRespVo = settlementService.couponValuation(couponValuationDto);
//        return ResponseData.OK(couponValuationRespVo);
//    }
    private Integer getPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE);
        Pattern pattern1 = Pattern.compile(MOBILE);
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        Matcher matcher1 = pattern1.matcher(phone);
        boolean matches1 = matcher1.matches();
        if (!matches && !matches1) {
            return 0;
        }
        return 1;
    }
}
