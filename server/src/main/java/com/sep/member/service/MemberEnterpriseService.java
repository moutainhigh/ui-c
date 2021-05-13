package com.sep.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.member.dto.MemberDto;
import com.sep.member.dto.MemberEnterpriseBackGroundDto;
import com.sep.member.dto.MemberEnterpriseDto;
import com.sep.member.dto.MemberUpdateDto;
import com.sep.member.model.MemberEnterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.member.vo.MemberEnterpriseBackGroundVo;
import com.sep.member.vo.MemberEnterpriseVo;
import com.sep.sku.dto.TokenDto;

/**
 * <p>
 *   服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
public interface MemberEnterpriseService extends IService<MemberEnterprise> {

    Integer add(MemberEnterpriseDto memberDto);

    MemberEnterpriseVo get(TokenDto tokenDto);

    IPage<MemberEnterpriseBackGroundVo> getBack(MemberEnterpriseBackGroundDto memberBackGroundDto);

    Integer updatesById(MemberUpdateDto memberUpdateDto);

    MemberEnterpriseVo getDetail(Integer id);
}
