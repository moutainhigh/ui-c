package com.sep.message.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.message.dto.PageSearchLetterRecordDto;
import com.sep.message.service.LetterRecordService;
import com.sep.message.vo.LetterRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后端信件相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "后端信件相关API", tags = {"后端信件相关API"})
@RestController
@RequestMapping("/background/letter-record")
public class BackgroundLetterRecordController {

    @Resource
    private LetterRecordService letterRecordService;

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<LetterRecordVo>> pageSearch(@RequestBody PageSearchLetterRecordDto dto) {
        return ResponseData.OK(letterRecordService.pageSearch(dto));
    }

    @GetMapping(value = "/details/{id}")
    @ApiOperation(value = "详情", httpMethod = "GET")
    public ResponseData<LetterRecordVo> findById(@PathVariable(value = "id") Integer id) {
        return ResponseData.OK(letterRecordService.findById(id));
    }

}