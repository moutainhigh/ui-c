package com.sep.message.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.message.dto.MySystemMessageDto;
import com.sep.message.dto.ReadMessageDto;
import com.sep.message.service.SystemMessageService;
import com.sep.message.vo.SystemMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 积分表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "小程序端系统消息相关API", tags = {"小程序端系统消息相关API"})
@RestController
@RequestMapping("/xcx/system-message")
public class SystemMessageController {

    @Resource
    private SystemMessageService systemMessageService;

    @PostMapping(value = "/my")
    @ApiOperation(value = "我的消息/分页查询", httpMethod = "POST")
    public ResponseData<IPage<SystemMessageVo>> pageSearch(@RequestBody MySystemMessageDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(systemMessageService.myMessages(dto));
    }

    @PostMapping(value = "/read")
    @ApiOperation(value = "阅读", httpMethod = "POST")
    public ResponseData<MySystemMessageDto> readMessage(@RequestBody ReadMessageDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(systemMessageService.readMessage(dto));
    }

}