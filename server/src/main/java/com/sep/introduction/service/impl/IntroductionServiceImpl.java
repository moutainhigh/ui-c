package com.sep.introduction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.introduction.dto.IntroductionDto;
import com.sep.introduction.model.Introduction;
import com.sep.introduction.repository.IntroductionMapper;
import com.sep.introduction.service.IntroductionService;
import com.sep.introduction.vo.IntroductionVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-24
 */
@Service
public class IntroductionServiceImpl extends ServiceImpl<IntroductionMapper, Introduction> implements IntroductionService {
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[0,5-9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$";
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 邮编正则表达式  [1-9]\d{5}(?!\d) 国内6位邮编
     */
    public static final String CODE = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    @Override
    public IntroductionVo get() {
        List<Introduction> list = lambdaQuery().list();
        if (list!=null&&list.size()>0){
            Introduction introduction = list.get(0);
            IntroductionVo introductionVo=new IntroductionVo();
            BeanUtils.copyProperties(introduction,introductionVo);
            return introductionVo;
        }
        return new IntroductionVo();
    }

    @Override
    public Integer add(IntroductionDto introductionDto) {
        List<Introduction> introductions = lambdaQuery().list();
        if (StringUtils.isNotEmpty(introductionDto.getPhone())){
            Integer phone = getPhone(introductionDto.getPhone());
            if (phone==0){
                throw new SepCustomException(ResponseData.STATUS_CODE_400,"请填写正确的号码");
            }
        }
        if (StringUtils.isNotEmpty(introductionDto.getEmail())){
            Integer email = getEmail(introductionDto.getEmail());
            if (email==0){
                throw new SepCustomException(ResponseData.STATUS_CODE_400,"请填写正确的邮箱");
            }
        }
        if (introductions!=null&&introductions.size()>0){
            Introduction introduction = introductions.get(0);
            BeanUtils.copyProperties(introductionDto,introduction);
            introduction.setUpdatedTime(LocalDateTime.now());
            return introduction.updateById()?1:0;
        }else {
            Introduction introduction = new Introduction();
            BeanUtils.copyProperties(introductionDto,introduction);
            introduction.setCreatedTime(LocalDateTime.now());
            return introduction.insert()?1:0;
        }
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
    private Integer getEmail(String email){
        Pattern pattern = Pattern.compile(CODE);
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        if (!matches) {
            return 0;
        }
        return 1;
    }
}
