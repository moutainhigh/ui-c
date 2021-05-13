package com.sep.point.controller.background;

import com.sep.common.model.response.ResponseData;
import com.sep.point.dto.SetSettingValueDto;
import com.sep.point.dto.UpdateSettingsDto;
import com.sep.point.service.SettingService;
import com.sep.point.vo.SettingVo;
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
 * @since 2020-02-13
 */
@Api(value = "后端系统设置相关API", tags = {"后端系统设置相关API"})
@RestController
@RequestMapping("/point/background/setting")
public class PointBackgroundSettingController {

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

    @PutMapping(value = "/updates")
    @ApiOperation(value = "批量修改", httpMethod = "PUT")
    public ResponseData<Boolean> updates(@RequestBody UpdateSettingsDto dto) {
        return ResponseData.OK(settingService.update(dto));
    }

}
