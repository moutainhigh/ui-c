package com.sep.content.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddActivityDto;
import com.sep.content.model.Activity;
import com.sep.content.model.ActivitySignUp;
import com.sep.content.service.ActivitySignUpService;
import com.sep.content.vo.ActivitySignUpVo;
import com.sep.sku.dto.IdDto;
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
 * 用户填写项表  前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-14
 */
@RestController
@Api(value = "管理后台活动填写项相关API", tags = {"管理后台活动填写项相关API"})
@RequestMapping("/background/activity")
public class ActivitySignUpController {
    @Autowired
    private ActivitySignUpService activitySignUpService;
    @PostMapping(value = "/get")
    @ApiOperation(value = "获取填写项", httpMethod = "POST")
    public ResponseData<ActivitySignUpVo> getActivity(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(activitySignUpService.get(idDto.getId()));
    }
}
