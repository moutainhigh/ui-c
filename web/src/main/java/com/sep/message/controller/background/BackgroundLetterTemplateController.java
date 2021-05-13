package com.sep.message.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.message.dto.DisableLetterTemplateDto;
import com.sep.message.dto.EnableLetterTemplateDto;
import com.sep.message.dto.PageSearchLetterTemplateDto;
import com.sep.message.service.LetterTemplateService;
import com.sep.message.vo.LetterTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后端信件模板相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "后端信件模板相关API", tags = {"后端信件模板相关API"})
@RestController
@RequestMapping("/background/letter-template")
public class BackgroundLetterTemplateController {

    @Resource
    private LetterTemplateService letterTemplateService;

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<LetterTemplateVo>> pageSearch(@RequestBody PageSearchLetterTemplateDto dto) {
        return ResponseData.OK(letterTemplateService.pageSearch(dto));
    }

    @GetMapping(value = "/details/{id}")
    @ApiOperation(value = "详情", httpMethod = "GET")
    public ResponseData<LetterTemplateVo> findById(@PathVariable(value = "id") Integer id) {
        return ResponseData.OK(letterTemplateService.findById(id));
    }

    @PutMapping(value = "/enable")
    @ApiOperation(value = "开启", httpMethod = "PUT")
    public ResponseData<Boolean> enable(@RequestBody EnableLetterTemplateDto dto) {
        return ResponseData.OK(letterTemplateService.enable(dto));
    }

    @PutMapping(value = "/disable")
    @ApiOperation(value = "关闭", httpMethod = "PUT")
    public ResponseData<Boolean> disable(@RequestBody DisableLetterTemplateDto dto) {
        return ResponseData.OK(letterTemplateService.disable(dto));
    }

}
