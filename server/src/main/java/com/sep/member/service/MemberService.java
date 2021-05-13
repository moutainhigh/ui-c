package com.sep.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.member.dto.MemberBackGroundDto;
import com.sep.member.dto.MemberDto;
import com.sep.member.dto.MemberUpdateDto;
import com.sep.member.model.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.member.vo.MemberBackGroundVo;
import com.sep.member.vo.MemberStateVo;
import com.sep.member.vo.MemberVo;
import com.sep.sku.dto.TokenDto;

/**
 * <p>
 *   服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
public interface MemberService extends IService<Member> {

    Integer add(MemberDto memberDto);

    MemberVo get(TokenDto token);

    IPage<MemberBackGroundVo> getBack(MemberBackGroundDto memberBackGroundDto);

    Integer updatesById(MemberUpdateDto id);

    MemberStateVo getState(TokenDto token);
}
