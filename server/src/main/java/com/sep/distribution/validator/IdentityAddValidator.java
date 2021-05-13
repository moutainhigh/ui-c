package com.sep.distribution.validator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sep.distribution.dto.IdentityAddDto;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.model.Identity;
import com.sep.distribution.repository.IdentityMapper;
import com.sep.common.exceptions.SepCustomException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IdentityAddValidator {

    @Resource
    private IdentityMapper identityMapper;

    public void validator(IdentityAddDto dto) {
        LambdaQueryWrapper<Identity> wrapper = new QueryWrapper<Identity>().lambda();
        wrapper.eq(Identity::getName, dto.getName());
        Integer count = identityMapper.selectCount(wrapper);
        if (count > 0) {
            throw new SepCustomException(BizErrorCode.IDENTITY_NAME_EXIST);
        }
    }

}