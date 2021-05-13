package com.sep.distribution.controller.xcx;


import com.sep.distribution.service.IdentityService;
import com.sep.distribution.vo.xcx.IdentityDetailsVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小程序分销身份相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/xcx/identity")
@Api(value = "小程序分销身份相关API", tags = {"小程序分销身份相关API"})
public class DistributionIdentityController {

    @Resource
    private IdentityService identityService;

    @GetMapping(value = "/search/enabled")
    @ApiOperation(value = "查询所有已经启用的", httpMethod = "GET")
    public ResponseData<List<IdentityDetailsVo>> searchEnabled() {
        return ResponseData.OK(identityService.searchEnabled());
    }

}