package com.sep.distribution.controller.background;


import com.sep.distribution.dto.SetSettingValueDto;
import com.sep.distribution.service.SettingService;
import com.sep.distribution.vo.background.SettingVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后端系统设置相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-08
 */
@Api(value = "后端系统设置相关API", tags = {"后端系统设置相关API"})
@RestController
@RequestMapping("/distribution/background/setting")
public class DistributionBackgroundSettingController {

    @Resource
    private SettingService settingService;

    @GetMapping(value = "/find/all")
    @ApiOperation(value = "查询所有", httpMethod = "GET")
    public ResponseData<List<SettingVo>> findAll() {
        return ResponseData.OK(settingService.findAll());
    }


    @PutMapping(value = "/update")
    @ApiOperation(value = "修改", httpMethod = "PUT")
    public ResponseData<Boolean> update(@RequestBody SetSettingValueDto dto) {
        return ResponseData.OK(settingService.setSettingValue(dto));
    }

}