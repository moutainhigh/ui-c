package com.sep.distribution.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.BaseDto;
import com.sep.distribution.dto.SearchWithdrawDto;
import com.sep.distribution.dto.WithdrawApplyDto;
import com.sep.distribution.service.SettingService;
import com.sep.distribution.service.WithdrawService;
import com.sep.distribution.vo.xcx.WithdrawDetailsVo;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 小程序提现相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/xcx/withdraw/apply")
@Api(value = "小程序提现相关API", tags = {"小程序提现相关API"})
public class DistributionWithdrawController {

    @Resource
    private WithdrawService withdrawService;

    @Autowired
    private SettingService settingService;

    @Value("${withdraw.amount}")
    private String withdrawAmount;

    @PostMapping(value = "/")
    @ApiOperation(value = "申请", httpMethod = "POST")
    public ResponseData<Boolean> apply(@RequestBody WithdrawApplyDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(withdrawService.apply(dto));
    }

    @PostMapping(value = "/pageSearch")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<WithdrawDetailsVo>> pageSearch(@RequestBody SearchWithdrawDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(withdrawService.pageSearch(dto));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "申请详情", httpMethod = "GET")
    public ResponseData<WithdrawDetailsVo> withdrawDetails(@PathVariable Integer id) {
        return ResponseData.OK(withdrawService.withdrawDetails(id));
    }

    @PostMapping(value = "/available")
    @ApiOperation(value = "可提现金额", httpMethod = "POST")
    public ResponseData<BigDecimal> available(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(withdrawService.available(userId));
    }

    @PostMapping(value = "/withdrawAmount")
    @ApiOperation(value = "最低提现金额", httpMethod = "POST")
    public ResponseData<BigDecimal> getWithdrawAmount() {
        return ResponseData.OK(settingService.getMinWithdraw());
    }


}