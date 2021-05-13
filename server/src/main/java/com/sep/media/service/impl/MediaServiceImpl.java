package com.sep.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.*;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.CollectType;
import com.sep.content.enums.CommonStatus;
import com.sep.content.enums.ObjType;
import com.sep.content.service.CollectService;
import com.sep.media.dto.AddMediaDto;
import com.sep.media.dto.SearchMediaDto;
import com.sep.media.model.Media;
import com.sep.media.model.MediaClassify;
import com.sep.media.repository.MediaMapper;
import com.sep.media.service.MediaClassifyService;
import com.sep.media.service.MediaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.media.vo.MediaVo;
import com.sep.media.vo.MediaXcxVo;
import com.sep.sku.dto.BatchSearchSkuInfoDto;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SkuInfoRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 合作方表  服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@Service
public class MediaServiceImpl extends ServiceImpl<MediaMapper, Media> implements MediaService {
    @Autowired
    private MediaClassifyService mediaClassifyService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CollectService collectService;

    @Override
    public Integer addAddMedia(AddMediaDto addMediaDto) {
        Integer result = 0;
        Media Media = new Media();
        BeanUtils.copyProperties(addMediaDto, Media);
        Media.setSurfaceImgUrl(addMediaDto.getSurfaceImgUrl());
        Media.setMediaClassifyId(addMediaDto.getMediaClassifyId());
        if (StringUtils.isNotBlank(Media.getSkuIds())) {
            String[] skuIds = Media.getSkuIds().split(",");
            if (skuIds != null && skuIds.length > 0) {
                for (String skuId : skuIds) {
                    if (StringUtils.isNotBlank(skuId)) {
                        if (!skuInfoService.isSkuOnlineStatus(Integer.parseInt(skuId))) {
                            throw new SepCustomException(BizErrorCode.NOTNULL_SKU);
                        }
                    }
                }
            }
        }
        if (Media.getId() != null && Media.getId() > 0) {
            Media original = getById(Media.getId());
            if (original != null && !original.getMediaClassifyId().equals(Media.getMediaClassifyId())) {
                IdDto id = new IdDto();
                id.setId(Media.getMediaClassifyId());
                mediaClassifyService.plusMediaNum(id);
                id.setId(original.getMediaClassifyId());
                mediaClassifyService.subtractMediaNum(id);
            }
            if (Media.getMediaClassifyId()==null||Media.getMediaClassifyId()==0){
                Media.setMediaClassifyId(0);
            }
            result = Media.updateById() ? 1 : 0;
        } else {
            result = Media.insert() ? 1 : 0;
            if (result > 0) {
                IdDto id = new IdDto();
                id.setId(Media.getMediaClassifyId());
                mediaClassifyService.plusMediaNum(id);
            }
        }
        return result;
    }

    @Override
    public IPage<MediaVo> searchMedia(SearchMediaDto searchMediaDto) {
        IPage<MediaVo> result = new Page<>();
        Page<Media> page = new Page<>(searchMediaDto.getCurrentPage(), searchMediaDto.getPageSize());
        IPage<Media> data = baseMapper.selectPage(page, new LambdaQueryWrapper<Media>()
                .like((StringUtils.isNotBlank(searchMediaDto.getTitle())), Media::getTitle, searchMediaDto.getTitle())
                .eq((searchMediaDto.getMediaClassifyId() != null && searchMediaDto.getMediaClassifyId() > 0), Media::getMediaClassifyId, searchMediaDto.getMediaClassifyId())
                .eq((searchMediaDto.getUpDownStatus() != null), Media::getUpDownStatus, searchMediaDto.getUpDownStatus())
                .eq((searchMediaDto.getIsRecommend() != null), Media::getIsRecommend, searchMediaDto.getIsRecommend())
                .orderByDesc(Media::getIsRecommend)
                .orderByDesc(Media::getRecommendTime)
                .orderByDesc(Media::getCreateTime)
        );
        if (data != null && data.getRecords() != null) {
            List<Integer> mediaClassifyIds = data.getRecords().stream().map(Media::getMediaClassifyId).collect(Collectors.toList());
            List<MediaClassify> MediaClassifyList =
                    mediaClassifyService.getMediaClassifys(mediaClassifyIds);
            List<MediaVo> list = data.getRecords().stream().map(e -> {
                MediaVo vo = new MediaVo();
                BeanUtils.copyProperties(e, vo);
                MediaClassifyList.forEach(item -> {
                    if (vo.getMediaClassifyId()!=null&&vo.getMediaClassifyId().equals(item.getId())) {
                        vo.setMediaClassifyName(item.getClassifyName());
                    }
                });
                vo.setCollecNum(collectService.getCollecNum(CollectType.MEDIA.getCode(), vo.getId()));
                if (StringUtils.isNotBlank(searchMediaDto.getToken())) {
                    CollectDto collectDto = new CollectDto();
                    collectDto.setToken(searchMediaDto.getToken());
                    collectDto.setObjType(CollectType.MEDIA.getCode());
                    collectDto.setObjId(vo.getId());
                    vo.setIsCollec(collectService.isCollec(collectDto));
                }
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
    public List<MediaXcxVo> searchXcx() {
        List<Media> list = lambdaQuery()
                .orderByDesc(Media::getIsRecommend)
                .orderByDesc(Media::getRecommendTime)
                .orderByDesc(Media::getMediaClassifyId)
                .orderByDesc(Media::getCreateTime).list();
        if (list != null) {
            List<Integer> mediaClassifyIds = list.stream().map(Media::getMediaClassifyId).collect(Collectors.toList());
            List<MediaClassify> mediaClassifies =
                    mediaClassifyService.getMediaClassifys(mediaClassifyIds);
            HashMap<String, List<MediaVo>> mediaVoHashMap = new HashMap<>();
            List<String> collect = list.stream().map(e -> {
                MediaVo vo = new MediaVo();
                BeanUtils.copyProperties(e, vo);
                mediaClassifies.forEach(item -> {
                    if (vo.getMediaClassifyId()!=null&&vo.getMediaClassifyId().equals(item.getId())) {
                        vo.setMediaClassifyName(item.getClassifyName());
                    }
                });
                String s = StringUtils.isEmpty(vo.getMediaClassifyName()) ? "0" : vo.getMediaClassifyName();
                List<MediaVo> mediaVo = mediaVoHashMap.get(s);
                if (Objects.isNull(mediaVo)) {
                    mediaVo = new ArrayList<>();
                }
                mediaVo.add(vo);
                mediaVoHashMap.put(s, mediaVo);
                return s;
//                vo.setCollecNum(collectService.getCollecNum(CollectType.MEDIA.getCode(), vo.getId()));
//                if(StringUtils.isNotBlank(searchMediaDto.getToken())){
//                    CollectDto collectDto=new CollectDto();
//                    collectDto.setToken(searchMediaDto.getToken());
//                    collectDto.setObjType(CollectType.MEDIA.getCode());
//                    collectDto.setObjId(vo.getId());
//                    vo.setIsCollec(collectService.isCollec(collectDto));
//                }
            }).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            List<MediaXcxVo> list1=new ArrayList<>();
            mediaClassifies.forEach(e->{
                MediaXcxVo mediaXcxVo=new MediaXcxVo();
                mediaXcxVo.setMediaClassifyName(e.getClassifyName());
                mediaXcxVo.setMediaVos(mediaVoHashMap.get(e.getClassifyName()));
                list1.add(mediaXcxVo);
            });
            if (!Objects.isNull(mediaVoHashMap.get("0"))){
                MediaXcxVo mediaXcxVo=new MediaXcxVo();
                mediaXcxVo.setMediaClassifyName("0");
                mediaXcxVo.setMediaVos(mediaVoHashMap.get("0"));
                list1.add(mediaXcxVo);
            }
            return list1;
        }
        return new ArrayList<>();
    }

    @Override
    public List<MediaVo> getHomeMediaTop3(SearchMediaDto searchMediaDto) {
        List<Media> list = lambdaQuery().eq((searchMediaDto.getMediaClassifyId() != null && searchMediaDto.getMediaClassifyId() > 0), Media::getMediaClassifyId, searchMediaDto.getMediaClassifyId())
                .eq(Media::getUpDownStatus, CommonStatus.YES.getCode())
                .eq(Media::getIsRecommend, CommonStatus.YES.getCode())
                .orderByAsc(Media::getIsRecommend)
                .orderByDesc(Media::getRecommendTime)
                .orderByDesc(Media::getCreateTime)
                .last("limit 0,3").list();
        if (list != null && list.size() > 0) {
            List<MediaVo> result = list.stream().map(e -> {
                MediaVo vo = new MediaVo();
                BeanUtils.copyProperties(e, vo);
                if (StringUtils.isNotBlank(searchMediaDto.getToken())) {
                    CollectDto collectDto = new CollectDto();
                    collectDto.setToken(searchMediaDto.getToken());
                    collectDto.setToken(searchMediaDto.getToken());
                    collectDto.setObjType(ObjType.MEDIA.getCode());
                    collectDto.setObjId(vo.getId());
                    vo.setIsCollec(collectService.isCollec(collectDto));
                }
                vo.setCollecNum(collectService.getCollecNum(CollectType.MEDIA.getCode(), vo.getId()));
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public MediaVo getMedia(IdDto idDto, Boolean isReturnSkus) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            MediaVo vo = new MediaVo();
            BeanUtils.copyProperties(Media, vo);
            if (isReturnSkus) {
                if (StringUtils.isNotBlank(Media.getSkuIds())) {
                    List<Integer> skuIds = Arrays.asList(Media.getSkuIds().split(",")).stream().map(e -> {
                        if (StringUtils.isNotBlank(e)) {
                            return Integer.parseInt(e);
                        }
                        return 0;
                    }).collect(Collectors.toList());
                    BatchSearchSkuInfoDto skuInfoDto = new BatchSearchSkuInfoDto();
                    skuInfoDto.setSkuIdList(skuIds);
                    List<SkuInfoRespVo> skus = skuInfoService.getSkuListByIds(skuInfoDto);
                    if (skus != null && skus.size() > 0) {
                        vo.setSkus(skus);
                    }
                }
            }
            return vo;

        }
        return null;
    }

    @Override
    public Integer delMedia(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            if (Media.getUpDownStatus().equals(CommonStatus.YES.getCode()))
                throw new SepCustomException(BizErrorCode.NOT_DEL);
            Integer result = Media.deleteById() ? 1 : 0;
            if (result > 0) {
                IdDto id = new IdDto();
                id.setId(Media.getMediaClassifyId());
                mediaClassifyService.subtractMediaNum(id);
            }
            return result;

        }
        return null;
    }

    @Override
    public Integer updateMediaUpDownStatus(UpdateStatusDto updateStatusDto) {
        Media Media = getById(updateStatusDto.getId());
        if (Media != null) {
            Media.setUpDownStatus(updateStatusDto.getStatus());
            return Media.updateById() ? 1 : 0;
        }

        return null;
    }

    @Override
    public Integer plusComment(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            Media.setCommentNum(Media.getCommentNum() + 1);
            return Media.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer subtracComment(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            Media.setCommentNum(Media.getCommentNum() - 1);
            return Media.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusPraise(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            Media.setPraiseNum(Media.getPraiseNum() + 1);
            return Media.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusRetransmissionNum(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            Media.setRetransmissionNum(Media.getRetransmissionNum() + 1);
            return Media.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer subtracPraise(IdDto idDto) {
        Media Media = getById(idDto.getId());
        if (Media != null) {
            Media.setPraiseNum(Media.getPraiseNum() - 1);
            return Media.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<MediaVo> getMediaVos(List<Integer> ids) {
        List<Media> list = lambdaQuery().in(Media::getId, ids).list();
        if (list != null && list.size() > 0) {
            List<MediaVo> result = list.stream().map(e -> {
                MediaVo vo = new MediaVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public Boolean isMediaOneline(Integer id) {

        Media Media = getById(id);
        if (Media == null)
            return false;
        if (Media.getUpDownStatus().equals(CommonStatus.NO.getCode()))
            return false;
        return true;
    }
}
