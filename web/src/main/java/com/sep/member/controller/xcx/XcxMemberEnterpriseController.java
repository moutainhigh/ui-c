package com.sep.member.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.member.dto.MemberDto;
import com.sep.member.dto.MemberEnterpriseDto;
import com.sep.member.service.MemberEnterpriseService;
import com.sep.member.vo.MemberEnterpriseVo;
import com.sep.sku.dto.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@RestController
@Api(value = "小程序-企业会员",tags = {"小程序-企业会员"})
@RequestMapping("/xcx/member/enterprise")
public class XcxMemberEnterpriseController {
    @Autowired
    private MemberEnterpriseService memberEnterpriseService;
    @ApiOperation("申请会员")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody MemberEnterpriseDto memberEnterpriseDto) {
        return ResponseData.OK(memberEnterpriseService.add(memberEnterpriseDto));
    }
    @ApiOperation("获取会员")
    @PostMapping("/get")
    public ResponseData<MemberEnterpriseVo> get(@RequestBody TokenDto tokenDto) {
        return ResponseData.OK(memberEnterpriseService.get(tokenDto));
    }
}
