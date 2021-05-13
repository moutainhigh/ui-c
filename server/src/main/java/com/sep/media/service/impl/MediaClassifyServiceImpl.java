package com.sep.media.service.impl;

import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.CommonStatus;
import com.sep.media.dto.AddMediaClassifyDto;
import com.sep.media.dto.UpdateMediaClassifySortDto;
import com.sep.media.model.MediaClassify;
import com.sep.media.repository.MediaClassifyMapper;
import com.sep.media.service.MediaClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.media.vo.MediaClassifyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章分类表  服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@Service
public class MediaClassifyServiceImpl extends ServiceImpl<MediaClassifyMapper, MediaClassify> implements MediaClassifyService {
    @Value("${mediaclassifyname}")
    private String defaultClassifyName;

    @Value("${defaultArticleClassifyId}")
    private Integer defaultMediaClassifyId;

    @Override
    public Integer addMediaClassify(AddMediaClassifyDto addMediaClassifyDto) {

        List<MediaClassify> list = lambdaQuery().eq(MediaClassify::getClassifyName, addMediaClassifyDto.getClassifyName()).list();
        if (list != null && list.size() > 1)
            throw new SepCustomException(BizErrorCode.EXISTS);
        Integer sortNum = lambdaQuery().eq(MediaClassify::getSort, addMediaClassifyDto.getSort()).count();
        if ((addMediaClassifyDto.getId() == null || addMediaClassifyDto.getId().equals(0)) && sortNum > 0) {
            throw new SepCustomException(BizErrorCode.SORT_ERROR);
        }

        MediaClassify mediaClassify = new MediaClassify();
        BeanUtils.copyProperties(addMediaClassifyDto, mediaClassify);
        if (mediaClassify.getId() != null && mediaClassify.getId() > 0) {
            return mediaClassify.updateById() ? 1 : 0;
        } else {
            return mediaClassify.insert() ? 1 : 0;
        }
    }

    @Override
    public Integer updateMediaClassifyStatus(UpdateStatusDto updateStatusDto) {
        MediaClassify mediaClassify = getById(updateStatusDto.getId());
        if (mediaClassify != null) {
            mediaClassify.setUpDownStatus(updateStatusDto.getStatus());
            return mediaClassify.updateById() ? 1 : 0;
        }
        return 0;
    }

    @Override
    public Integer UpdateMediaClassifySort(UpdateMediaClassifySortDto updateMediaClassifySortDto) {
        MediaClassify mediaClassify = getById(updateMediaClassifySortDto.getId());
        if (mediaClassify != null) {
            mediaClassify.setSort(mediaClassify.getSort());
            return mediaClassify.updateById() ? 1 : 0;
        }
        return 0;
    }

    @Override
    public List<MediaClassifyVo> getMediaClassifysBack() {

        List<MediaClassify> list = lambdaQuery()
                .orderByAsc(MediaClassify::getSort).orderByDesc(MediaClassify::getCreateTime).list();
        if (list != null && list.size() > 0) {
            List<MediaClassifyVo> result = list.stream().map(e -> {
                MediaClassifyVo vo = new MediaClassifyVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public List<MediaClassifyVo> getMediaClassifysXcx(Integer top) {
        List<MediaClassify> list = new ArrayList<>();
        if (top != null && top > 0) {
            list = lambdaQuery().eq(MediaClassify::getUpDownStatus, CommonStatus.YES.getCode())
                    .orderByAsc(MediaClassify::getSort).orderByDesc(MediaClassify::getCreateTime).last("limit 0," + top).list();
        } else {
            list = lambdaQuery().eq(MediaClassify::getUpDownStatus, CommonStatus.YES.getCode())
                    .orderByAsc(MediaClassify::getSort).orderByDesc(MediaClassify::getCreateTime).list();
        }
        if (list != null && list.size() > 0) {
            List<MediaClassifyVo> result = list.stream().map(e -> {
                MediaClassifyVo vo = new MediaClassifyVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            if (top != null && top > 0) {
                MediaClassifyVo homeVo = new MediaClassifyVo();
                homeVo.setId(0);
                homeVo.setClassifyName(defaultClassifyName);
                result.add(0, homeVo);
            }
            return result;
        }
        return null;
    }

    @Override
    public List<MediaClassifyVo> getMediaClassifysXcxForRedhouse() {
        List<MediaClassifyVo> result=new ArrayList<>();
        MediaClassifyVo homeVo = new MediaClassifyVo();
        homeVo.setId(0);
        homeVo.setClassifyName(defaultClassifyName);
        result.add(0, homeVo);
        MediaClassify mediaClassify=getById(defaultMediaClassifyId);
        if(mediaClassify!=null){
            MediaClassifyVo vo = new MediaClassifyVo();
            BeanUtils.copyProperties(mediaClassify, vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public MediaClassifyVo getMediaClassify(IdDto idDto) {
        MediaClassify mediaClassify = getById(idDto.getId());
        if (mediaClassify != null) {
            MediaClassifyVo vo = new MediaClassifyVo();
            BeanUtils.copyProperties(mediaClassify, vo);
            return vo;
        }
        return null;
    }

    @Override
    public Integer subtractMediaNum(IdDto idDto) {
        MediaClassify mediaClassify = getById(idDto.getId());
        if (mediaClassify != null && mediaClassify.getMediaNum()>0) {
            mediaClassify.setMediaNum(mediaClassify.getMediaNum() - 1);
            return mediaClassify.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusMediaNum(IdDto idDto) {
        MediaClassify mediaClassify = getById(idDto.getId());
        if (mediaClassify != null) {
            mediaClassify.setMediaNum(mediaClassify.getMediaNum() + 1);
            return mediaClassify.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer delMediaClassify(IdDto idDto) {
        MediaClassify mediaClassify = getById(idDto.getId());
        if (mediaClassify != null) {
            if (mediaClassify.getMediaNum() > 0)
                throw new SepCustomException(BizErrorCode.NOT_DEL);
            return mediaClassify.deleteById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<MediaClassify> getMediaClassifys(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {

            return lambdaQuery().in(MediaClassify::getId, ids).orderByAsc(MediaClassify::getSort).list();

        }
        return null;
    }
}
