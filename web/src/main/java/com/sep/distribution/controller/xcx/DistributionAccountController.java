package com.sep.distribution.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.BaseDto;
import com.sep.distribution.dto.DistributionOrderDto;
import com.sep.distribution.dto.FansSearchDto;
import com.sep.distribution.service.AccountService;
import com.sep.distribution.service.CashBackService;
import com.sep.distribution.vo.xcx.AccountDetailsVo;
import com.sep.distribution.vo.xcx.DistributionOrderVo;
import com.sep.distribution.vo.xcx.FansVo;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 小程序账号相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/xcx/account")
@Api(value = "小程序账号相关API", tags = {"小程序账号相关API"})
public class DistributionAccountController {

    @Resource
    private AccountService accountService;
    @Resource
    private CashBackService cashBackService;

    @PostMapping(value = "/details")
    @ApiOperation(value = "账号详情(余额和累计收益)", httpMethod = "POST")
    public ResponseData<AccountDetailsVo> detail(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(accountService.detail(userId));
    }

    @PostMapping(value = "/today/earnings")
    @ApiOperation(value = "今日收益", httpMethod = "POST")
    public ResponseData<BigDecimal> todayEarnings(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(cashBackService.todayEarnings(userId));
    }

    @PostMapping(value = "/today/order")
    @ApiOperation(value = "今日返现订单数量统计", httpMethod = "POST")
    public ResponseData<Integer> todayOrder(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(cashBackService.todayOrder(userId));
    }

    @PostMapping(value = "/addup/order")
    @ApiOperation(value = "累计返现订单数量统计", httpMethod = "POST")
    public ResponseData<Integer> addUpOrder(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(cashBackService.addUpOrder(userId));
    }

    @PostMapping(value = "/stairfans")
    @ApiOperation(value = "一级粉丝", httpMethod = "POST")
    public ResponseData<IPage<FansVo>> stairFans(@RequestBody FansSearchDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(accountService.stairFans(dto));
    }

    @PostMapping(value = "/secondlevelfans")
    @ApiOperation(value = "二级粉丝", httpMethod = "POST")
    public ResponseData<IPage<FansVo>> secondLevelFans(@RequestBody FansSearchDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(accountService.secondLevelFans(dto));
    }

    @PostMapping(value = "/order")
    @ApiOperation(value = "分销订单", httpMethod = "POST")
    public ResponseData<IPage<DistributionOrderVo>> distributionOrder(@RequestBody DistributionOrderDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(accountService.distributionOrder(dto));
    }

}