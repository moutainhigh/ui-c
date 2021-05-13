package com.sep.member.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.member.dto.MemberBackGroundDto;
import com.sep.member.dto.MemberUpdateDto;
import com.sep.member.model.Member;
import com.sep.member.service.MemberService;
import com.sep.member.vo.MemberBackGroundVo;
import com.sep.sku.dto.IdDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@RestController
@Api(value = "后端-个人会员相关api", tags = {"后端-个人会员相关api"})
@RequestMapping("/background/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/get")
    @ApiOperation("获取会员列表")
    public ResponseData<IPage<MemberBackGroundVo>> get(@RequestBody MemberBackGroundDto memberBackGroundDto) {

        return ResponseData.OK(memberService.getBack(memberBackGroundDto));
    }
    @PostMapping("/update")
    @ApiOperation("修改会员状态")
    public ResponseData<Integer> update(@RequestBody MemberUpdateDto memberUpdateDto) {

        return ResponseData.OK(memberService.updatesById(memberUpdateDto));
    }

}
