package com.sep.distribution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.dto.*;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.model.Identity;
import com.sep.distribution.repository.IdentityMapper;
import com.sep.distribution.service.IdentityService;
import com.sep.distribution.validator.IdentityAddValidator;
import com.sep.distribution.validator.IdentityUpdateValidator;
import com.sep.distribution.vo.background.IdentityPageSearchVo;
import com.sep.distribution.vo.background.IdentitySelectVo;
import com.sep.distribution.vo.xcx.IdentityDetailsVo;
import com.sep.common.enums.YesNo;
import com.sep.common.exceptions.SepCustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 分销身份表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Service
public class IdentityServiceImpl extends ServiceImpl<IdentityMapper, Identity> implements IdentityService {

    @Resource
    private IdentityAddValidator identityAddValidator;
    @Resource
    private IdentityUpdateValidator identityUpdateValidator;

    @Override
    public List<IdentityDetailsVo> searchEnabled() {
        LambdaQueryWrapper<Identity> wrapper = new QueryWrapper<Identity>().lambda();
        wrapper.orderByDesc(Identity::getId);
        wrapper.eq(Identity::getEnable, YesNo.NO.getCode());
        List<Identity> identities = this.list(wrapper);
        return identities.stream().map(identity -> {
            IdentityDetailsVo vo = new IdentityDetailsVo();
            BeanUtils.copyProperties(identity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Identity> getEnabledById(Integer id) {
        LambdaQueryWrapper<Identity> wrapper = new QueryWrapper<Identity>().lambda();
        wrapper.eq(Identity::getId, id);
        wrapper.eq(Identity::getEnable, YesNo.YES.getCode());
        return Optional.of(this.getOne(wrapper));
    }

    @Override
    public boolean add(IdentityAddDto dto) {
        identityAddValidator.validator(dto);
        Identity identity = new Identity();
        BeanUtils.copyProperties(dto, identity);
        identity.setCreateTime(LocalDateTime.now());
        identity.setUpdateTime(LocalDateTime.now());
        identity.setUpdateUid(dto.getCreateUid());
        identity.setEnable(YesNo.YES.getCode());
        if (this.save(identity)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.IDENTITY_ADD_ERROR);
    }

    @Override
    public boolean update(IdentityUpdateDto dto) {
        identityUpdateValidator.validator(dto);
        Identity identity = new Identity();
        BeanUtils.copyProperties(dto, identity);
        identity.setUpdateTime(LocalDateTime.now());
        if (this.updateById(identity)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.IDENTITY_UPDATE_ERROR);
    }

    @Override
    public boolean enable(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Identity> wrapper = new UpdateWrapper<Identity>().lambda();
        wrapper.set(Identity::getEnable, YesNo.NO.getCode());
        wrapper.set(Identity::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Identity::getId, dto.getId());
        if (this.update(wrapper)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.IDENTITY_ENABLE_ERROR);
    }

    @Override
    public boolean disable(BaseUpdateDto dto) {
        LambdaUpdateWrapper<Identity> wrapper = new UpdateWrapper<Identity>().lambda();
        wrapper.set(Identity::getEnable, YesNo.YES.getCode());
        wrapper.set(Identity::getUpdateUid, dto.getUpdateUid());
        wrapper.eq(Identity::getId, dto.getId());
        if (this.update(wrapper)) {
            return true;
        }
        throw new SepCustomException(BizErrorCode.IDENTITY_DISABLE_ERROR);
    }

    @Override
    public IPage<IdentityPageSearchVo> pageSearch(IdentityPageSearchDto dto) {
        LambdaQueryWrapper<Identity> lambda = new QueryWrapper<Identity>().lambda();
        lambda.orderByDesc(Identity::getId);
        if (Objects.nonNull(dto.getEnable())) {
            lambda.eq(Identity::getEnable, dto.getEnable());
        }
        if (Objects.nonNull(dto.getCashBackWay())) {
            lambda.eq(Identity::getCashBackWay, dto.getCashBackWay());
        }
        IPage<Identity> page = this.page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        List<IdentityPageSearchVo> collect = page.getRecords().stream().map(identity -> {
            IdentityPageSearchVo vo = new IdentityPageSearchVo();
            BeanUtils.copyProperties(identity, vo);
            return vo;
        }).collect(Collectors.toList());
        Page<IdentityPageSearchVo> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(collect);
        return result;
    }

    @Override
    public List<IdentitySelectVo> selectAll() {
        List<Identity> list = this.list();
        return list.stream().map(identity -> {
            IdentitySelectVo vo = new IdentitySelectVo();
            BeanUtils.copyProperties(identity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IdentityDto get() {
        List<Identity> list = this.list();
        if (list != null && list.size() > 0) {
            IdentityDto identityDto = new IdentityDto();
            BeanUtils.copyProperties(list.get(0), identityDto);
            return identityDto;
        }
        return null;
    }

}