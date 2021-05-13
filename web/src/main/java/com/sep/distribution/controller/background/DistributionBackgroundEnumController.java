package com.sep.distribution.controller.background;


import com.sep.distribution.service.EnumService;
import com.sep.distribution.vo.xcx.EnumVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台枚举相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/background/enum")
@Api(value = "后台枚举相关API", tags = {"后台枚举相关API"})
public class DistributionBackgroundEnumController {

    @Resource
    private EnumService enumService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询所有", httpMethod = "GET")
    public ResponseData<List<EnumVo>> list() {
        return ResponseData.OK(enumService.find());
    }

    @GetMapping(value = "/{type}")
    @ApiOperation(value = "根据类型id查询", httpMethod = "GET")
    public ResponseData<EnumVo> find(@PathVariable String type) {
        return ResponseData.OK(enumService.find(type));
    }

}