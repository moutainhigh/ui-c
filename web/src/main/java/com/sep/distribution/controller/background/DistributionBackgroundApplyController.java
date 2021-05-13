package com.sep.distribution.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.*;
import com.sep.distribution.service.ApplyService;
import com.sep.distribution.view.excel.DistributionUserView;
import com.sep.distribution.vo.background.*;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后端分销申请相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/background/apply")
@Api(value = "后端分销申请相关API", tags = {"后端分销申请相关API"})
public class DistributionBackgroundApplyController {

    @Resource
    private ApplyService applyService;

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<ApplyPageSearchVo>> pageSearch(@RequestBody ApplyPageSearchDto dto) {
        return ResponseData.OK(applyService.pageSearch(dto));
    }

    @PutMapping(value = "/approve")
    @ApiOperation(value = "通过", httpMethod = "PUT")
    public ResponseData<Boolean> approve(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(applyService.approve(dto));
    }

    @PutMapping(value = "/reject")
    @ApiOperation(value = "驳回", httpMethod = "PUT")
    public ResponseData<Boolean> reject(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(applyService.reject(dto));
    }

    @GetMapping(value = "/realName/{id}")
    @ApiOperation(value = "实名信息", httpMethod = "GET")
    public ResponseData<ApplyRealNameVo> realName(@PathVariable("id") Integer id) {
        return ResponseData.OK(applyService.realName(id));
    }

    @PostMapping(value = "/users")
    @ApiOperation(value = "分销用户", httpMethod = "POST")
    public ResponseData<IPage<DistributionUserVo>> users(@RequestBody DistributionUserSearchDto dto) {
        return ResponseData.OK(applyService.users(dto));
    }

    @PostMapping(value = "/stair/fans")
    @ApiOperation(value = "一级粉丝分页查询", httpMethod = "POST")
    public ResponseData<IPage<StairFansVo>> stairFans(@RequestBody FansUserSearchDto dto) {
        return ResponseData.OK(applyService.stairFans(dto));
    }

    @PostMapping(value = "/secondlevel/fans")
    @ApiOperation(value = "二级粉丝分页查询", httpMethod = "POST")
    public ResponseData<IPage<SecondLevelFansVo>> secondLevelFans(@RequestBody FansUserSearchDto dto) {
        return ResponseData.OK(applyService.secondLevelFans(dto));
    }

    @PostMapping(value = "/users/export")
    @ApiOperation(value = "分销用户导出", httpMethod = "POST")
    public ModelAndView usersExport(@RequestBody DistributionUserExportDto dto) {
        DistributionUserSearchDto searchDto = new DistributionUserSearchDto();
        BeanUtils.copyProperties(dto,searchDto);
        searchDto.setPageSize(Long.MAX_VALUE);
        IPage<DistributionUserVo> page = applyService.users(searchDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getRecords());
        return new ModelAndView(new DistributionUserView(), map);
    }

}