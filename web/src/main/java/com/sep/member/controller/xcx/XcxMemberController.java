package com.sep.member.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.member.dto.MemberDto;
import com.sep.member.dto.MemberEnterpriseDto;
import com.sep.member.model.MemberEnterprise;
import com.sep.member.service.MemberEnterpriseService;
import com.sep.member.service.MemberService;
import com.sep.member.vo.MemberStateVo;
import com.sep.member.vo.MemberVo;
import com.sep.sku.dto.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.Token;
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
@Api(value = "小程序-会员相关api", tags = {"小程序-会员相关api"})
@RequestMapping("/xcx/member")
public class XcxMemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation("申请会员")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody MemberDto memberDto) {
        return ResponseData.OK(memberService.add(memberDto));
    }

    @ApiOperation("获取会员")
    @PostMapping("/get")
    public ResponseData<MemberVo> get(@RequestBody TokenDto token) {
        return ResponseData.OK(memberService.get(token));
    }

    @ApiOperation("查看用户会员状态")
    @PostMapping("/getState")
    public ResponseData<MemberStateVo> getState(@RequestBody TokenDto token) {
        return ResponseData.OK(memberService.getState(token));
    }
}
