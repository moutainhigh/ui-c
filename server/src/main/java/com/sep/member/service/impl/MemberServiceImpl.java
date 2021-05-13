package com.sep.member.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.content.model.Collect;
import com.sep.member.dto.MemberBackGroundDto;
import com.sep.member.dto.MemberDto;
import com.sep.member.dto.MemberUpdateDto;
import com.sep.member.model.Member;
import com.sep.member.model.MemberEnterprise;
import com.sep.member.repository.MemberMapper;
import com.sep.member.service.MemberEnterpriseService;
import com.sep.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.member.vo.MemberBackGroundVo;
import com.sep.member.vo.MemberEnterpriseVo;
import com.sep.member.vo.MemberStateVo;
import com.sep.member.vo.MemberVo;
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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberEnterpriseService enterpriseService;
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[0,5-9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\\\d{8}$\";";
    @Override
    public Integer add(MemberDto memberDto) {
        String userId = JwtUtils.parseJWT(memberDto.getToken()).get("id").toString();
        if (lambdaQuery().eq(Member::getState, 2).eq(Member::getUserID, userId).count() > 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您已申请过个人会员，请勿重复申请");
        }
        if (enterpriseService.lambdaQuery().eq(MemberEnterprise::getUserID,userId).count()>0){
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您已申请过企业会员");
        }
        if(StringUtils.isNotEmpty(userId)){
            if(getPhone(memberDto.getPhone())==0){
                throw new SepCustomException(ResponseData.STATUS_CODE_400,"请填写正确的手机号码");
            }
            List<Member> list = lambdaQuery().eq(Member::getUserID, userId).list();
            Member member = new Member();
            BeanUtils.copyProperties(memberDto, member);
            member.setUserID(Integer.parseInt(userId));
            if((memberDto.getId()!=null&&memberDto.getId()!=0)||( !CollectionUtils.isEmpty(list)&&list.size()>0&&list.get(0)!=null)){
                member.setId(list.get(0).getId());
                member.setUpdatedTime(LocalDateTime.now());
                member.setState(0);
                return member.updateById() ? 1 : 0;
            }else {
                member.setCreatedTime(LocalDateTime.now());
                return member.insert() ? 1 : 0;
            }
        }
        return 0;
    }

    @Override
    public MemberVo get(TokenDto token) {
        String userId = JwtUtils.parseJWT(token.getToken()).get("id").toString();
        Member memberEnterprise = lambdaQuery().eq(Member::getUserID, userId).one();
        if(memberEnterprise==null){
            return new MemberVo();
        }
        MemberVo memberEnterpriseVo = new MemberVo();
        BeanUtils.copyProperties(memberEnterprise, memberEnterpriseVo);
        return memberEnterpriseVo;
    }

    @Override
    public IPage<MemberBackGroundVo> getBack(MemberBackGroundDto memberBackGroundDto) {
        IPage<Member> iPage=new Page<>();
        iPage.setCurrent(memberBackGroundDto.getCurrentPage());
        iPage.setSize(memberBackGroundDto.getPageSize());
        QueryWrapper<Member> memberQueryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<Member> lambda = memberQueryWrapper.lambda();
        lambda.like(memberBackGroundDto.getName()!=null,Member::getName,memberBackGroundDto.getName())
                .eq(memberBackGroundDto.getSex()!=null,Member::getSex,memberBackGroundDto.getSex())
        .orderByDesc(Member::getCreatedTime).orderByDesc(Member::getState);
        IPage<Member> iPage1 = baseMapper.selectPage(iPage, lambda);
        IPage<MemberBackGroundVo> memberBackGroundVoIPage=new Page<>();
        BeanUtils.copyProperties(iPage1,memberBackGroundVoIPage);
        memberBackGroundVoIPage.setRecords(iPage1.getRecords().stream().map(e->{
            MemberBackGroundVo memberBackGroundVo=new MemberBackGroundVo();
            BeanUtils.copyProperties(e,memberBackGroundVo);
            return memberBackGroundVo;
        }).collect(Collectors.toList()));
        return memberBackGroundVoIPage;
    }

    @Override
    public Integer updatesById(MemberUpdateDto id) {
        Member member = this.getById(id.getId());
        if (member==null){
            return 0;
        }
//        Integer count = lambdaQuery().eq(Member::getUserID, member.getUserID()).eq(Member::getState, 1).count();
//        if(count>0){
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "该用户已存在会员");
//        }
        member.setState(id.getState());
        member.setUpdatedTime(LocalDateTime.now());
        return member.updateById()?1:0;
    }

    @Override
    public MemberStateVo getState(TokenDto token) {
        String userId = JwtUtils.parseJWT(token.getToken()).get("id").toString();
        Integer count = lambdaQuery().eq(Member::getState,2).eq(Member::getUserID, userId).count();
        Integer counte = enterpriseService.lambdaQuery()
                .eq(MemberEnterprise::getState,2).eq(MemberEnterprise::getUserID, userId).count();
        MemberStateVo memberStateVo=new MemberStateVo();
        memberStateVo.setState(0);
        if (count>0){
            memberStateVo.setState(1);
        }
        if (counte>0){
            memberStateVo.setState(2);
        }
        return memberStateVo;
    }

    private Integer getPhone(String phone){
        Pattern pattern = Pattern.compile(PHONE);
        Pattern pattern1 = Pattern.compile(MOBILE);
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        Matcher matcher1 = pattern1.matcher(phone);
        boolean matches1 = matcher1.matches();
        if (!matches&&!matches1) {
            return 0;
        }
        return 1;
    }
}
