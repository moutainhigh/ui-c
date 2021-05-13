package com.sep.introduction.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.introduction.service.IntroductionService;
import com.sep.introduction.vo.IntroductionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-24
 */
@RestController
@Api(value = "小程序公司简介接口", tags = {"小程序公司简介接口"})
@RequestMapping("/xcx/introduction")
public class XcxIntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @GetMapping("/get")
    @ApiOperation("获取公司简介")
    public ResponseData<IntroductionVo> get() {
        return ResponseData.OK(introductionService.get());
    }
}
