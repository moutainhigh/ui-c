package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.*;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.CollectType;
import com.sep.content.enums.CommonStatus;
import com.sep.content.enums.ObjType;
import com.sep.content.model.Article;
import com.sep.content.model.ArticleClassify;
import com.sep.content.repository.ArticleMapper;
import com.sep.content.service.ArticleClassifyService;
import com.sep.content.service.ArticleService;
import com.sep.content.service.CollectService;
import com.sep.content.vo.ArticleVo;
import com.sep.sku.dto.BatchSearchSkuInfoDto;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SkuInfoRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleClassifyService articleClassifyService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CollectService collectService;

    @Override
    public Integer addAddArticle(AddArticleDto addArticleDto) {
        Integer result = 0;
        Article article = new Article();
        BeanUtils.copyProperties(addArticleDto, article);
        if (StringUtils.isNotBlank(article.getSkuIds())) {
            String[] skuIds = article.getSkuIds().split(",");
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
        if (article.getId() != null && article.getId() > 0) {
            Article original = getById(article.getId());
            if (original != null && !original.getArticleClassifyId().equals(article.getArticleClassifyId())) {
                IdDto id = new IdDto();
                id.setId(article.getArticleClassifyId());
                articleClassifyService.plusArticleNum(id);
                id.setId(original.getArticleClassifyId());
                articleClassifyService.subtractArticleNum(id);
            }
            result = article.updateById() ? 1 : 0;
        } else {
            result = article.insert() ? 1 : 0;
            if (result > 0) {
                IdDto id = new IdDto();
                id.setId(article.getArticleClassifyId());
                articleClassifyService.plusArticleNum(id);
            }
        }
        return result;
    }

    @Override
    public IPage<ArticleVo> searchArticle(SearchArticleDto searchArticleDto) {
        IPage<ArticleVo> result = new Page<>();
        Page<Article> page = new Page<>(searchArticleDto.getCurrentPage(), searchArticleDto.getPageSize());
        IPage<Article> data = baseMapper.selectPage(page, new LambdaQueryWrapper<Article>()
                .like((StringUtils.isNotBlank(searchArticleDto.getTitle())), Article::getTitle, searchArticleDto.getTitle())
                .eq((searchArticleDto.getArticleClassifyId() != null && searchArticleDto.getArticleClassifyId() > 0), Article::getArticleClassifyId, searchArticleDto.getArticleClassifyId())
                .eq((searchArticleDto.getUpDownStatus() != null), Article::getUpDownStatus, searchArticleDto.getUpDownStatus())
                .eq((searchArticleDto.getIsRecommend() != null), Article::getIsRecommend, searchArticleDto.getIsRecommend())
                .orderByDesc(Article::getIsRecommend)
                .orderByDesc(Article::getRecommendTime)
                .orderByDesc(Article::getCreateTime)
        );
        if (data != null && data.getRecords() != null) {
            List<Integer> articleClassifyIds = data.getRecords().stream().map(e -> e.getArticleClassifyId()).collect(Collectors.toList());
            List<ArticleClassify> articleClassifyList = articleClassifyService.getArticleClassifys(articleClassifyIds);
            List<ArticleVo> list = data.getRecords().stream().map(e -> {
                ArticleVo vo = new ArticleVo();
                BeanUtils.copyProperties(e, vo);
                articleClassifyList.forEach(item -> {
                    if (vo.getArticleClassifyId().equals(item.getId())) {
                        vo.setArticleClassifyName(item.getClassifyName());
                    }
                });
                vo.setCollecNum(collectService.getCollecNum(CollectType.ARTICLE.getCode(), vo.getId()));
                if(StringUtils.isNotBlank(searchArticleDto.getToken())){
                    CollectDto collectDto=new CollectDto();
                    collectDto.setToken(searchArticleDto.getToken());
                    collectDto.setObjType(CollectType.ARTICLE.getCode());
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
    public List<ArticleVo> getHomeArticleTop3(SearchArticleDto searchArticleDto) {
        List<Article> list = lambdaQuery().eq((searchArticleDto.getArticleClassifyId() != null && searchArticleDto.getArticleClassifyId() > 0), Article::getArticleClassifyId, searchArticleDto.getArticleClassifyId())
                .eq(Article::getUpDownStatus, CommonStatus.YES.getCode())
                .orderByAsc(Article::getIsRecommend)
                .orderByDesc(Article::getRecommendTime)
                .orderByDesc(Article::getCreateTime).list();
        if (list != null && list.size() > 0) {
            List<ArticleVo> result = list.stream().map(e -> {
                ArticleVo vo = new ArticleVo();
                BeanUtils.copyProperties(e, vo);
                if (StringUtils.isNotBlank(searchArticleDto.getToken())) {
                    CollectDto collectDto = new CollectDto();
                    collectDto.setToken(searchArticleDto.getToken());
                    collectDto.setToken(searchArticleDto.getToken());
                    collectDto.setObjType(ObjType.ARTICLE.getCode());
                    collectDto.setObjId(vo.getId());
                    vo.setIsCollec(collectService.isCollec(collectDto));
                }
                vo.setCollecNum(collectService.getCollecNum(CollectType.ARTICLE.getCode(), vo.getId()));
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public ArticleVo getArticle(IdDto idDto, Boolean isReturnSkus) {
        Article article = getById(idDto.getId());
        if (article != null) {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(article, vo);
            if (isReturnSkus) {
                if (StringUtils.isNotBlank(article.getSkuIds())) {
                    List<Integer> skuIds = Arrays.asList(article.getSkuIds().split(",")).stream().map(e -> {
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
    public Integer delArticle(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            if (article.getUpDownStatus().equals(CommonStatus.YES.getCode()))
                throw new SepCustomException(BizErrorCode.NOT_DEL);
            Integer result = article.deleteById() ? 1 : 0;
            if (result > 0) {
                IdDto id = new IdDto();
                id.setId(article.getArticleClassifyId());
                articleClassifyService.subtractArticleNum(id);
            }
            return result;

        }
        return null;
    }

    @Override
    public Integer updateArticleUpDownStatus(UpdateStatusDto updateStatusDto) {
        Article article = getById(updateStatusDto.getId());
        if (article != null) {
            article.setUpDownStatus(updateStatusDto.getStatus());
            return article.updateById() ? 1 : 0;
        }

        return null;
    }

    @Override
    public Integer plusComment(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            article.setCommentNum(article.getCommentNum() + 1);
            return article.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer subtracComment(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            article.setCommentNum(article.getCommentNum() - 1);
            return article.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusPraise(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            article.setPraiseNum(article.getPraiseNum() + 1);
            return article.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusRetransmissionNum(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            article.setRetransmissionNum(article.getRetransmissionNum() + 1);
            return article.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer subtracPraise(IdDto idDto) {
        Article article = getById(idDto.getId());
        if (article != null) {
            article.setPraiseNum(article.getPraiseNum() - 1);
            return article.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<ArticleVo> getArticleVos(List<Integer> ids) {
        List<Article> list = lambdaQuery().in(Article::getId, ids).list();
        if (list != null && list.size() > 0) {
            List<ArticleVo> result = list.stream().map(e -> {
                ArticleVo vo = new ArticleVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public Boolean isArticleOneline(Integer id) {

        Article article = getById(id);
        if (article == null)
            return false;
        if (article.getUpDownStatus().equals(CommonStatus.NO.getCode()))
            return false;
        return true;
    }
}
