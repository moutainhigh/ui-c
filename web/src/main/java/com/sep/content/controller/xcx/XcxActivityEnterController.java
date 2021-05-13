package com.sep.content.controller.xcx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.AddEnterDto;
import com.sep.content.dto.SearchEnterSignUpDto;
import com.sep.content.dto.SearchEnterSignUpXcxDto;
import com.sep.content.service.ActivityEnterService;
import com.sep.content.service.ActivityEnterSignUpService;
import com.sep.content.service.ActivitySignUpService;
import com.sep.content.vo.ActivityEnterSignUpVo;
import com.sep.content.vo.ActivityEnterSignUpXcxVo;
import com.sep.content.vo.EnterRespVo;
import com.sep.sku.tool.Paytool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/xcx/activityEnter")
@Api(value = "小程序活动报名报名 API", tags = {"小程序活动报名报名 API"})
public class XcxActivityEnterController {

    @Autowired
    private ActivityEnterSignUpService activityEnterSignUpService;

    @PostMapping(value = "/enter")
    @ApiOperation(value = "活动报名", httpMethod = "POST")
    public ResponseData<EnterRespVo> enter(@RequestBody @Valid AddEnterDto addEnterDto,
                                           HttpServletRequest request) {
        addEnterDto.setIp(Paytool.getRemoteAddrIp(request));
        return ResponseData.OK(activityEnterSignUpService.enter(addEnterDto));
    }
    @PostMapping(value = "/searchActivityEnterSignUp")
    @ApiOperation(value = "查询活动报名列表", httpMethod = "POST")
    public ResponseData<ActivityEnterSignUpXcxVo> searchActivityEnterSignUp(@RequestBody SearchEnterSignUpXcxDto searchEnterDto) {
        SearchEnterSignUpDto searchEnterSignUpDto=new SearchEnterSignUpDto();
        int userId = (int) JwtUtils.parseJWT(searchEnterDto.getToken()).get("id");
        searchEnterSignUpDto.setUserId(userId);
        searchEnterSignUpDto.setActiveId(searchEnterDto.getActiveId());
        return ResponseData.OK(activityEnterSignUpService.searchActivityXcxEnter(searchEnterSignUpDto));
    }

}
