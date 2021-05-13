package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.CollectDto;
import com.sep.content.dto.SearchColleDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.CollectType;
import com.sep.content.model.Collect;
import com.sep.content.repository.CollectMapper;
import com.sep.content.service.ArticleService;
import com.sep.content.service.CollectService;
import com.sep.content.service.IncreaseIntegral;
import com.sep.content.vo.ArticleVo;
import com.sep.content.vo.CollectVo;
import com.sep.sku.dto.BatchSearchSkuInfoDto;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SkuInfoRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private ArticleService articleService;



    @Autowired
    private IncreaseIntegral increaseIntegral;

    @Override
    public Integer addCollec(CollectDto collectDto) {
        Integer result = null;
        String userId = JwtUtils.parseJWT(collectDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Integer isCollec = lambdaQuery().eq(Collect::getUserId, Integer.parseInt(userId))
                    .eq(Collect::getObjType, collectDto.getObjType())
                    .eq(Collect::getObjId, collectDto.getObjId()).count();
            if (isCollec == 0) {
                Collect collect = new Collect();
                BeanUtils.copyProperties(collectDto, collect);
                collect.setUserId(Integer.parseInt(userId));
                result = collect.insert() ? 1 : 0;
                if (result > 0) {
                    increaseIntegral.increase(collect.getUserId(),6);


                }
            }
        }
        return result;
    }

    @Override
    public Integer deCollec(CollectDto collectDto) {
        Integer result = null;
        String userId = JwtUtils.parseJWT(collectDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            List<Collect> lsit = lambdaQuery().eq(Collect::getUserId, Integer.parseInt(userId))
                    .eq(Collect::getObjType, collectDto.getObjType())
                    .eq(Collect::getObjId, collectDto.getObjId()).list();
            if (lsit != null && lsit.size() > 0) {
                Collect collect = lsit.get(0);
                result = collect.deleteById() ? 1 : 0;
            }
        }
        return result;
    }

    @Override
    public Integer isCollec(CollectDto collectDto) {
        String userId = JwtUtils.parseJWT(collectDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Integer isCollec = lambdaQuery().eq(Collect::getUserId, Integer.parseInt(userId))
                    .eq(Collect::getObjType, collectDto.getObjType())
                    .eq(Collect::getObjId, collectDto.getObjId()).count();
            return isCollec;
        } else {
            throw new SepCustomException(BizErrorCode.EXISTS);
        }

    }

    @Override
    public Integer getCollecNum(Integer objType, Integer objId) {
        return lambdaQuery().eq(Collect::getObjType, objType)
                .eq(Collect::getObjId, objId).count();
    }

    @Override
    public IPage<CollectVo> searchColle(SearchColleDto searchColleDto) {
        IPage<CollectVo> result = new Page<>();
        String userId = JwtUtils.parseJWT(searchColleDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Page<Collect> page = new Page<>(searchColleDto.getCurrentPage(), searchColleDto.getPageSize());
            IPage<Collect> data = baseMapper.selectPage(page, new LambdaQueryWrapper<Collect>()
                    .eq(Collect::getUserId, Integer.parseInt(userId))
                    .eq(Collect::getObjType, searchColleDto.getObjType())
                    .orderByDesc(Collect::getCreateTime)
            );
            if (data != null && data.getRecords() != null) {
                List<Integer> objIds = data.getRecords().stream().map(e -> e.getObjId()).collect(Collectors.toList());
                List<CollectVo> list = new ArrayList<>();
                if (searchColleDto.getObjType().equals(CollectType.SKU.getCode())) {
                    BatchSearchSkuInfoDto skuInfoDto = new BatchSearchSkuInfoDto();
                    skuInfoDto.setSkuIdList(objIds);
                    List<SkuInfoRespVo> skus = skuInfoService.getSkuListByIds(skuInfoDto);
                    list = data.getRecords().stream().map(e -> {
                        CollectVo vo = new CollectVo();
                        BeanUtils.copyProperties(e, vo);
                        skus.forEach(item -> {
                            if (vo.getObjId().equals(item.getId())) {
                                vo.setSkus(item);
                            }
                        });
                        return vo;
                    }).collect(Collectors.toList());

                } else if (searchColleDto.getObjType().equals(CollectType.ARTICLE.getCode())) {
                    List<ArticleVo> articleVos = articleService.getArticleVos(objIds);
                    list = data.getRecords().stream().map(e -> {
                        CollectVo vo = new CollectVo();
                        BeanUtils.copyProperties(e, vo);
                        articleVos.forEach(item -> {
                            if (vo.getObjId().equals(item.getId())) {
                                vo.setArticleVo(item);
                            }
                        });
                        return vo;
                    }).collect(Collectors.toList());

                }
                result.setPages(data.getPages());
                result.setCurrent(data.getCurrent());
                result.setTotal(data.getTotal());
                result.setRecords(list);
            }

        }
        return result;
    }
}
