package com.sep.sku.controller.background;


import com.google.common.collect.Lists;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.IdDto;
import com.sep.sku.dto.FacilitatorDto;
import com.sep.sku.dto.SearchFacilitatorDto;
import com.sep.sku.enums.ExpressCompanyEnum;
import com.sep.sku.service.FacilitatorService;
import com.sep.sku.vo.SearchExpressRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/background/facilitator")
@Api(value = "管理后台服务商相关api", tags = {"管理后台服务商相关api"})
public class BackgroundFacilitatorController {


    @Autowired
    private FacilitatorService facilitatorService;

    @PostMapping(value = "/addOrUpdateFacilitator")
    @ApiOperation(value = "添加或者修改服务商", httpMethod = "POST")
    public ResponseData<Integer> addOrUpdateFacilitator(@RequestBody FacilitatorDto facilitatorDto) {
        return ResponseData.OK(facilitatorService.addOrUpdateFacilitator(facilitatorDto));
    }

    @PostMapping(value = "/getFacilitatorDto")
    @ApiOperation(value = "获取服务商详情", httpMethod = "POST")
    public ResponseData<FacilitatorDto> getFacilitatorDto(@RequestBody IdDto idDto) {
        return ResponseData.OK(facilitatorService.getFacilitatorDto(idDto));
    }

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询全部服务商", httpMethod = "POST")
    public ResponseData<List<FacilitatorDto>> getList( @RequestBody SearchFacilitatorDto searchFacilitatorDto) {
        return ResponseData.OK(facilitatorService.getList(searchFacilitatorDto));
    }

    @PostMapping(value = "/delFacilitator")
    @ApiOperation(value = "删除服务商", httpMethod = "POST")
    public ResponseData<Integer> delFacilitator( @RequestBody IdDto idDto) {
        return ResponseData.OK(facilitatorService.delFacilitator(idDto));
    }
}
