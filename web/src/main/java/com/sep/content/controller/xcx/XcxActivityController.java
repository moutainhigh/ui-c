package com.sep.content.controller.xcx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
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
import java.util.List;

@RestController
@RequestMapping("/xcx/activity")
@Api(value = "小程序活动 API", tags = {"小程序活动 API"})
public class XcxActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping(value = "/getActivity")
    @ApiOperation(value = "获取活动", httpMethod = "POST")
    public ResponseData<ActivityVo> getActivity(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(activityService.getActivity(idDto, true));
    }

    @PostMapping(value = "/getHomeActivityVo")
    @ApiOperation(value = "首页活动", httpMethod = "POST")
    public ResponseData<List<ActivityVo>> getHomeActivityVo() {
        return ResponseData.OK(activityService.getHomeActivityVo());
    }

    @PostMapping(value = "/searchActivity")
    @ApiOperation(value = "活动列表", httpMethod = "POST")
    public ResponseData<IPage<ActivityVo>> searchActivity(@RequestBody SearchActivityDto searchActivityDto) {
        return ResponseData.OK(activityService.searchActivity(searchActivityDto));
    }

}
