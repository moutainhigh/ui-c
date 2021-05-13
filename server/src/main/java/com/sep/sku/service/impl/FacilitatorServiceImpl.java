package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sep.content.dto.IdDto;
import com.sep.content.vo.ActivityVo;
import com.sep.sku.dto.FacilitatorDto;
import com.sep.sku.dto.SearchFacilitatorDto;
import com.sep.sku.model.Facilitator;
import com.sep.sku.repository.FacilitatorMapper;
import com.sep.sku.service.FacilitatorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-05-19
 */
@Service
public class FacilitatorServiceImpl extends ServiceImpl<FacilitatorMapper, Facilitator> implements FacilitatorService {

    @Override
    public Integer addOrUpdateFacilitator(FacilitatorDto facilitatorDto) {
        Integer result = 0;
        Facilitator facilitator = new Facilitator();
        BeanUtils.copyProperties(facilitatorDto, facilitator);
        if (!Objects.isNull(facilitatorDto.getId()) && facilitatorDto.getId() > 0) {
            facilitator.setTs(LocalDateTime.now());
            result = facilitator.updateById() ? 1 : 0;
        } else {
            facilitator.setCreateTime(LocalDateTime.now());
            facilitator.setTs(LocalDateTime.now());
            result = facilitator.insert() ? 1 : 0;
        }
        return result;
    }

    @Override
    public FacilitatorDto getFacilitatorDto(IdDto idDto) {
        Facilitator facilitator = getById(idDto.getId());
        if (facilitator == null)
            return null;
        FacilitatorDto result = new FacilitatorDto();
        BeanUtils.copyProperties(facilitator, result);
        return result;
    }

    @Override
    public List<FacilitatorDto> getList(SearchFacilitatorDto searchFacilitatorDto) {
        return lambdaQuery().like(Facilitator::getFacilitatorName, searchFacilitatorDto.getFacilitatorName())
                .like(Facilitator::getLeadingOfficial, searchFacilitatorDto.getLeadingOfficial())
                .like(Facilitator::getPhoneNum, searchFacilitatorDto.getPhoneNum()).orderByDesc(Facilitator::getCreateTime).list().stream().map(e -> {
                    FacilitatorDto vo = new FacilitatorDto();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    public Integer delFacilitator(IdDto idDto) {
        Facilitator facilitator = getById(idDto.getId());
        if (facilitator == null)
            return null;
        //TODO 服务商关联商品不可删除
        return facilitator.deleteById() ? 1 : 0;
    }

    @Override
    public List<Facilitator> getListByIds(List<Integer> ids) {

        if (CollectionUtils.isEmpty(ids)) {

            return null;

        }

        return lambdaQuery().in(Facilitator::getId, ids).list();
    }
}
