package com.sep.message.controller.server;


import com.sep.common.model.response.ResponseData;
import com.sep.message.dto.SendLetterInput;
import com.sep.message.service.LetterTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * Server端信件相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "Server端信件相关API", tags = {"Server端信件相关API"})
@RestController
@RequestMapping("/server/letter")
public class ServerLetterController {

    @Resource
    private LetterTemplateService letterTemplateService;

    @PostMapping(value = "/send")
    @ApiOperation(value = "发送信件", httpMethod = "POST")
    public ResponseData<Boolean> send(@RequestBody SendLetterInput dto) {
        return ResponseData.OK(letterTemplateService.send(dto));
    }

}