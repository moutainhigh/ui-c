package com.sep.content.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;

import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddActivityDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchActivityDto;
import com.sep.content.service.ActivityService;
import com.sep.content.vo.ActivityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/activity")
@Api(value = "管理后台活动相关API", tags = {"管理后台活动相关API"})
public class BackActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping(value = "/addActivity")
    @ApiOperation(value = "添加活动", httpMethod = "POST")
    public ResponseData<Integer> addActivity(@RequestBody @Valid AddActivityDto addActivityDto) {
        return ResponseData.OK(activityService.addActivity(addActivityDto));
    }

    @PostMapping(value = "/getActivity")
    @ApiOperation(value = "获取活动", httpMethod = "POST")
    public ResponseData<ActivityVo> getActivity(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(activityService.getActivity(idDto, true));
    }

    @PostMapping(value = "/searchActivity")
    @ApiOperation(value = "活动列表", httpMethod = "POST")
    public ResponseData<IPage<ActivityVo>> searchActivity(@RequestBody SearchActivityDto searchActivityDto) {
        return ResponseData.OK(activityService.searchActivity(searchActivityDto));
    }

    @PostMapping(value = "/delActivity")
    @ApiOperation(value = "删除活动", httpMethod = "POST")
    public ResponseData<Integer> delActivity(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(activityService.delActivity(idDto));
    }
}
