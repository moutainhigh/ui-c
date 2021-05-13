package com.sep.content.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.SearchEnterDto;
import com.sep.content.dto.SearchEnterSignUpDto;
import com.sep.content.service.ActivityEnterService;
import com.sep.content.service.ActivityEnterSignUpService;
import com.sep.content.vo.ActivityEnterSignUpVo;
import com.sep.content.vo.ActivityEnterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/background/activityEnter")
@Api(value = "管理后台活动报名API", tags = {"管理后台活动报名API"})
public class BackActivityEnterController {


    @Autowired
    private ActivityEnterService activityEnterService;
    @Autowired
    private ActivityEnterSignUpService activityEnterSignUpService;
//    @PostMapping(value = "/searchActivityEnter")
//    @ApiOperation(value = "查询活动报名列表", httpMethod = "POST")
//    public ResponseData<IPage<ActivityEnterVo>> searchActivityEnter(@RequestBody SearchEnterDto searchEnterDto) {
//        return ResponseData.OK(activityEnterService.searchActivityEnter(searchEnterDto));
//    }


    @PostMapping(value = "/searchActivityEnterSignUp")
    @ApiOperation(value = "查询活动报名列表", httpMethod = "POST")
    public ResponseData<ActivityEnterSignUpVo> searchActivityEnterSignUp(@RequestBody SearchEnterSignUpDto searchEnterDto) {
        return ResponseData.OK(activityEnterSignUpService.searchActivityEnter(searchEnterDto));
    }
}
