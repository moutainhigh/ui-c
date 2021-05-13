package com.sep.message.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.message.service.EnumService;
import com.sep.message.vo.EnumVo;
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
 * 小程序分销身份相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/background/enum")
@Api(value = "后端枚举相关API", tags = {"后端枚举相关API"})
public class BackgroundEnumController {

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