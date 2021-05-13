package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.AddBannerDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchBannerDto;
import com.sep.content.enums.BannerObjType;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.Banner;
import com.sep.content.repository.BannerMapper;
import com.sep.content.service.ActivityService;
import com.sep.content.service.ArticleService;
import com.sep.content.service.BannerService;
import com.sep.content.vo.BannerVo;
import com.sep.sku.service.SkuInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点配图表 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    public Integer addOrUpdateBanner(AddBannerDto addBannerDto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(addBannerDto, banner);
        if (addBannerDto.getObjType() != null && addBannerDto.getObjId() != null) {
            if (banner.getObjType().equals(BannerObjType.ACTIVITY.getCode())) {
                if (!activityService.isActivityOneLine(banner.getObjId())) {
                    throw new SepCustomException(BizErrorCode.NOTNILL_ACTIVITY);
                }
            }
            if (banner.getObjType().equals(BannerObjType.ARTICLE.getCode())) {
                if (!articleService.isArticleOneline(banner.getObjId())) {
                    throw new SepCustomException(BizErrorCode.NOTNILL_ARTICLE);
                }
            }
            if (banner.getObjType().equals(BannerObjType.SKU.getCode())) {
                if (!skuInfoService.isSkuOnlineStatus(banner.getObjId())) {
                    throw new SepCustomException(BizErrorCode.NOTNULL_SKU);
                }
            }
        }
        Integer sortNum = lambdaQuery().eq(Banner::getType, addBannerDto.getType()).eq(Banner::getSort, addBannerDto.getSort()).count();
        if ((banner.getId() == null || banner.getId().equals(0)) && sortNum > 0) {
            throw new SepCustomException(BizErrorCode.SORT_ERROR);
        }
        if (banner.getId() != null && banner.getId() > 0) {
            return banner.updateById() ? 1 : 0;
        } else {
            return banner.insert() ? 1 : 0;
        }
    }

    @Override
    public BannerVo getBanner(IdDto idDto) {
        Banner banner = getById(idDto.getId());
        if (banner != null) {
            BannerVo vo = new BannerVo();
            BeanUtils.copyProperties(banner, vo);
            return vo;
        }
        return null;
    }

    @Override
    public Integer delBanner(IdDto idDto) {

        return baseMapper.deleteById(idDto.getId());
    }

    @Override
    public IPage<BannerVo> searchBanner(SearchBannerDto searchBannerDto) {
        IPage<BannerVo> result = new Page<>();
        Page<Banner> page = new Page<>(searchBannerDto.getCurrentPage(), searchBannerDto.getPageSize());
        IPage<Banner> data = baseMapper.selectPage(page, new LambdaQueryWrapper<Banner>()
                .eq((searchBannerDto.getType() != null && searchBannerDto.getType() > 0), Banner::getType, searchBannerDto.getType())
                .orderByAsc(Banner::getSort).orderByDesc(Banner::getCreateTime));
        if (data != null && data.getRecords() != null) {
            List<BannerVo> list = data.getRecords().stream().map(e -> {
                BannerVo vo = new BannerVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            result.setPages(data.getPages());
            result.setCurrent(data.getCurrent());
            result.setTotal(data.getTotal());
            result.setRecords(list);
        }
        return result;
    }

    @Override
    public List<BannerVo> getBannersByType(SearchBannerDto searchBannerDto) {
        List<Banner> list = lambdaQuery().eq((searchBannerDto.getType() != null && searchBannerDto.getType() > 0), Banner::getType, searchBannerDto.getType())
                .eq(Banner::getIsShow, 1)
                .orderByAsc(Banner::getSort).orderByDesc(Banner::getCreateTime).list();
        if (list != null && list.size() > 0) {
            return list.stream().map(e -> {
                BannerVo vo = new BannerVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
