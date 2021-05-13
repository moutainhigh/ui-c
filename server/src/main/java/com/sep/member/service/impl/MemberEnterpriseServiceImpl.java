package com.sep.member.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.member.dto.*;
import com.sep.member.model.Member;
import com.sep.member.model.MemberEnterprise;
import com.sep.member.repository.MemberEnterpriseMapper;
import com.sep.member.service.MemberEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.member.service.MemberService;
import com.sep.member.vo.MemberBackGroundVo;
import com.sep.member.vo.MemberEnterpriseBackGroundVo;
import com.sep.member.vo.MemberEnterpriseVo;
import com.sep.sku.dto.TokenDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@Service
public class MemberEnterpriseServiceImpl extends ServiceImpl<MemberEnterpriseMapper, MemberEnterprise> implements MemberEnterpriseService {
    @Autowired
    private MemberService memberService;
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[0,5-9])\d{8}$
     * /^(13[0-9]|14[01456879]|15[0-3,5-9]|16[2567]|17[0-8]|18[0-9]|19[0-3,5-9])\d{8}$/
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$";

    @Override
    public Integer add(MemberEnterpriseDto memberDto) {
        String userId = JwtUtils.parseJWT(memberDto.getToken()).get("id").toString();
        if (lambdaQuery().eq(MemberEnterprise::getState, 2).eq(MemberEnterprise::getUserID, userId).count() > 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您已申请过企业会员，请勿重复申请");
        }
        if (memberService.lambdaQuery().eq(Member::getUserID, userId).count() > 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您已申请过个人会员");
        }
        if (StringUtils.isNotEmpty(userId)) {
            if (getPhone(memberDto.getContactsLandline()) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的电话号码");
            }
            if (getPhone(memberDto.getPersonLandline()) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的电话号码");
            }
            if (getMobile(memberDto.getPersonPhone()) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的手机号码");
            }
            if (getMobile(memberDto.getContactsPhone()) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的手机号码");
            }
            MemberEnterprise member = new MemberEnterprise();
            BeanUtils.copyProperties(memberDto, member);
            member.setUserID(Integer.parseInt(userId));
            List<MemberEnterprise> list = lambdaQuery().eq(MemberEnterprise::getUserID, userId).list();
            if ((memberDto.getId() != null && memberDto.getId() != 0) || (!CollectionUtils.isEmpty(list) && list.size() > 0 && list.get(0) != null)) {
                member.setUpdatedTime(LocalDateTime.now());
                member.setId(list.get(0).getId());
                member.setState(0);
                return member.updateById() ? 1 : 0;
            } else {
                member.setCreatedTime(LocalDateTime.now());
                return member.insert() ? 1 : 0;
            }

        }
        return 0;
    }

    @Override
    public MemberEnterpriseVo get(TokenDto tokenDto) {
        String userId = JwtUtils.parseJWT(tokenDto.getToken()).get("id").toString();
        MemberEnterprise memberEnterprise = lambdaQuery().eq(MemberEnterprise::getUserID, userId).one();
        if (memberEnterprise == null) {
            return new MemberEnterpriseVo();
        }
        MemberEnterpriseVo memberEnterpriseVo = new MemberEnterpriseVo();
        BeanUtils.copyProperties(memberEnterprise, memberEnterpriseVo);
        return memberEnterpriseVo;
    }

    @Override
    public IPage<MemberEnterpriseBackGroundVo> getBack(MemberEnterpriseBackGroundDto memberBackGroundDto) {
        IPage<MemberEnterprise> iPage = new Page<>();
        iPage.setCurrent(memberBackGroundDto.getCurrentPage());
        iPage.setSize(memberBackGroundDto.getPageSize());
        QueryWrapper<MemberEnterprise> memberQueryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberEnterprise> lambda = memberQueryWrapper.lambda();
        lambda.like(memberBackGroundDto.getName() != null, MemberEnterprise::getName, memberBackGroundDto.getName())
                .like(memberBackGroundDto.getContactsName() != null, MemberEnterprise::getContactsName, memberBackGroundDto.getContactsName())
                .like(memberBackGroundDto.getPersonName() != null, MemberEnterprise::getPersonName, memberBackGroundDto.getPersonName())
                .orderByDesc(MemberEnterprise::getCreatedTime).orderByDesc(MemberEnterprise::getState);
        IPage<MemberEnterprise> iPage1 = baseMapper.selectPage(iPage, lambda);
        IPage<MemberEnterpriseBackGroundVo> memberBackGroundVoIPage = new Page<>();
        BeanUtils.copyProperties(iPage1, memberBackGroundVoIPage);
        memberBackGroundVoIPage.setRecords(iPage1.getRecords().stream().map(e -> {
            MemberEnterpriseBackGroundVo memberBackGroundVo = new MemberEnterpriseBackGroundVo();
            BeanUtils.copyProperties(e, memberBackGroundVo);
            return memberBackGroundVo;
        }).collect(Collectors.toList()));
        return memberBackGroundVoIPage;
    }

    @Override
    public Integer updatesById(MemberUpdateDto memberUpdateDto) {
        MemberEnterprise byId = this.getById(memberUpdateDto.getId());
        if (byId == null) {
            return 0;
        }
        byId.setState(memberUpdateDto.getState());
        return byId.updateById() ? 1 : 0;
    }

    @Override
    public MemberEnterpriseVo getDetail(Integer id) {
        MemberEnterprise memberEnterprise = lambdaQuery().eq(MemberEnterprise::getId, id).one();
        if (memberEnterprise == null) {
            return new MemberEnterpriseVo();
        }
        Integer count = lambdaQuery().eq(MemberEnterprise::getUserID, memberEnterprise.getUserID()).eq(MemberEnterprise::getState, 1).count();
        if (count > 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "该用户已存在会员");
        }
        MemberEnterpriseVo memberEnterpriseVo = new MemberEnterpriseVo();
        BeanUtils.copyProperties(memberEnterprise, memberEnterpriseVo);
        return memberEnterpriseVo;
    }

    private Integer getPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE);
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        if (!matches) {
            return 0;
        }
        return 1;
    }

    private Integer getMobile(String phone) {
        Pattern pattern1 = Pattern.compile(MOBILE);
        Matcher matcher1 = pattern1.matcher(phone);
        boolean matches1 = matcher1.matches();
        if (!matches1) {
            return 0;
        }
        return 1;
    }
}
