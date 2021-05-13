package com.sep.distribution.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.*;
import com.sep.distribution.service.IdentityService;
import com.sep.distribution.vo.background.IdentityPageSearchVo;
import com.sep.distribution.vo.background.IdentitySelectVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后端分销身份相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/background/identity")
@Api(value = "后端分销身份相关API", tags = {"后端分销身份相关API"})
public class DistributionBackgroundIdentityController {

    @Resource
    private IdentityService identityService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加", httpMethod = "POST")
    public ResponseData<Boolean> add(@RequestBody IdentityAddDto dto) {
        return ResponseData.OK(identityService.add(dto));
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改", httpMethod = "PUT")
    public ResponseData<Boolean> update(@RequestBody IdentityUpdateDto dto) {
        return ResponseData.OK(identityService.update(dto));
    }

    @PutMapping(value = "/get")
    @ApiOperation(value = "修改", httpMethod = "PUT")
    public ResponseData<IdentityDto> get() {
        return ResponseData.OK(identityService.get());
    }

    @PutMapping(value = "/enable")
    @ApiOperation(value = "启用", httpMethod = "PUT")
    public ResponseData<Boolean> enable(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(identityService.enable(dto));
    }

    @PutMapping(value = "/disable")
    @ApiOperation(value = "停用", httpMethod = "PUT")
    public ResponseData<Boolean> disable(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(identityService.disable(dto));
    }

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<IdentityPageSearchVo>> pageSearch(@RequestBody IdentityPageSearchDto dto) {
        return ResponseData.OK(identityService.pageSearch(dto));
    }

    @GetMapping(value = "/select/all")
    @ApiOperation(value = "查询所有", httpMethod = "GET")
    public ResponseData<List<IdentitySelectVo>> selectAll() {
        return ResponseData.OK(identityService.selectAll());
    }

}