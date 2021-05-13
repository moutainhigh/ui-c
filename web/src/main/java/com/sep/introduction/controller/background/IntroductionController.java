package com.sep.introduction.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.introduction.dto.IntroductionDto;
import com.sep.introduction.model.Introduction;
import com.sep.introduction.service.IntroductionService;
import com.sep.introduction.vo.IntroductionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.oval.guard.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-24
 */
@RestController
@Api(value = "公司简介接口",tags = {"公司简介接口"})
@RequestMapping("/background/introduction")
public class IntroductionController {
    @Autowired
    private IntroductionService introductionService;
    @GetMapping("/get")
    @ApiOperation("获取公司简介")
    public ResponseData<IntroductionVo> get(){
        return ResponseData.OK(introductionService.get());
    }
    @ApiOperation("添加公司简介")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody IntroductionDto introductionDto){

        return ResponseData.OK(introductionService.add(introductionDto));
    }

}
