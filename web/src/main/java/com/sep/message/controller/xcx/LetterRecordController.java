package com.sep.message.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.message.dto.MyLetterDto;
import com.sep.message.dto.ReadLetterDto;
import com.sep.message.service.LetterRecordService;
import com.sep.message.vo.MyLetterRecordVo;
import com.sep.sku.dto.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 小程序端信件相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
@Api(value = "小程序端信件相关API", tags = {"小程序端信件相关API"})
@RestController
@RequestMapping("/xcx/letter-record")
public class LetterRecordController {

    @Resource
    private LetterRecordService letterRecordService;

    @PostMapping(value = "/my")
    @ApiOperation(value = "我的信件/分页查询", httpMethod = "POST")
    public ResponseData<IPage<MyLetterRecordVo>> pageSearch(@RequestBody MyLetterDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(letterRecordService.myLetters(dto));
    }

    @PostMapping(value = "/read")
    @ApiOperation(value = "阅读", httpMethod = "POST")
    public ResponseData<MyLetterRecordVo> readMessage(@RequestBody ReadLetterDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(letterRecordService.readLetter(dto));
    }

    @PostMapping(value = "/isRead")
    @ApiOperation(value = "是否有未阅读", httpMethod = "POST")
    public ResponseData<MyLetterRecordVo> readMessage(@RequestBody TokenDto tokenDto) {
        int userId = (int) JwtUtils.parseJWT(tokenDto.getToken()).get("id");
        return ResponseData.OK(letterRecordService.isRead(userId));
    }




}