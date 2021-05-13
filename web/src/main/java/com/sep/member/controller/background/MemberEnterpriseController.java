package com.sep.member.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.member.dto.MemberBackGroundDto;
import com.sep.member.dto.MemberEnterpriseBackGroundDto;
import com.sep.member.dto.MemberUpdateDto;
import com.sep.member.model.MemberEnterprise;
import com.sep.member.service.MemberEnterpriseService;
import com.sep.member.service.MemberService;
import com.sep.member.vo.MemberBackGroundVo;
import com.sep.member.vo.MemberEnterpriseBackGroundVo;
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
 *   前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@RestController
@Api(value = "后端-企业会员相关api",tags = {"后端-企业会员相关api"})
@RequestMapping("/background/member/enterprise")
public class MemberEnterpriseController {
    @Autowired
    private MemberEnterpriseService memberEnterpriseService;

    @PostMapping("/get")
    @ApiOperation("获取会员列表")
    public ResponseData<IPage<MemberEnterpriseBackGroundVo>> get(@RequestBody MemberEnterpriseBackGroundDto memberBackGroundDto) {

        return ResponseData.OK(memberEnterpriseService.getBack(memberBackGroundDto));
    }
    @PostMapping("/update")
    @ApiOperation("修改会员状态")
    public ResponseData<Integer> update(@RequestBody MemberUpdateDto memberUpdateDto) {

        return ResponseData.OK(memberEnterpriseService.updatesById(memberUpdateDto));
    }
    @PostMapping("/getDetail")
    @ApiOperation("获取会员详情")
    public ResponseData<IPage<MemberEnterpriseBackGroundVo>> getDetail(@RequestBody IdDto idDto) {

        return ResponseData.OK(memberEnterpriseService.getDetail(idDto.getId()));
    }
}
