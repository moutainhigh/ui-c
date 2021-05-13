package com.sep.point.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.point.service.SettingService;
import com.sep.point.vo.SettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小程序端系统设置相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Api(value = "小程序端系统设置相关API", tags = {"小程序端系统设置相关API"})
@RestController
@RequestMapping("/point/xcx/setting")
public class PointXcxSettingController {

    @Resource
    private SettingService settingService;

    @GetMapping(value = "/find/all")
    @ApiOperation(value = "查询所有", httpMethod = "GET")
    public ResponseData<List<SettingVo>> findAll() {
        return ResponseData.OK(settingService.findAll());
    }

}
