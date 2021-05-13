package com.sep.distribution.controller.server;


import com.sep.distribution.dto.CashbackDto;
import com.sep.distribution.service.CashBackService;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后台返现相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/server/cashback")
@Api(value = "后台返现相关API", tags = {"后台返现相关API"})
public class DistributionCashBackServer {

    @Resource
    private CashBackService cashBackService;

    @PostMapping(value = "/")
    @ApiOperation(value = "返现", httpMethod = "POST")
    public ResponseData<Boolean> cashback(@RequestBody CashbackDto dto) {
        return ResponseData.OK(cashBackService.cashback(dto));
    }

}