package com.sep.message.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.message.dto.*;
import com.sep.message.service.SystemMessageService;
import com.sep.message.vo.SystemMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后端系统消息相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "后端系统消息相关API", tags = {"后端系统消息相关API"})
@RestController
@RequestMapping("/background/system-message")
public class BackgroundSystemMessageController {

    @Resource
    private SystemMessageService systemMessageService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加", httpMethod = "POST")
    public ResponseData<Boolean> add(@RequestBody AddSystemMessageDto dto) {
        return ResponseData.OK(systemMessageService.add(dto));
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改", httpMethod = "PUT")
    public ResponseData<Boolean> update(@RequestBody UpdateSystemMessageDto dto) {
        return ResponseData.OK(systemMessageService.update(dto));
    }

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<SystemMessageVo>> pageSearch(@RequestBody PageSearchSystemMessageDto dto) {
        return ResponseData.OK(systemMessageService.pageSearch(dto));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除", httpMethod = "DELETE")
    public ResponseData<Boolean> update(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(systemMessageService.delete(dto));
    }

    @GetMapping(value = "/details/{id}")
    @ApiOperation(value = "详情", httpMethod = "GET")
    public ResponseData<SystemMessageVo> findById(@PathVariable(value = "id") Integer id) {
        return ResponseData.OK(systemMessageService.findById(id));
    }

    @PutMapping(value = "/publish")
    @ApiOperation(value = "发布", httpMethod = "PUT")
    public ResponseData<Boolean> publish(@RequestBody PublishSystemMessageDto dto) {
        return ResponseData.OK(systemMessageService.publish(dto));
    }

    @PutMapping(value = "/suspended")
    @ApiOperation(value = "撤回", httpMethod = "PUT")
    public ResponseData<Boolean> suspended(@RequestBody SuspendedSystemMessageDto dto) {
        return ResponseData.OK(systemMessageService.suspended(dto));
    }

}