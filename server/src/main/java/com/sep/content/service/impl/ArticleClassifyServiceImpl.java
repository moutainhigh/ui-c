package com.sep.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.AddArticleClassifyDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateArticleClassifySortDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.CommonStatus;
import com.sep.content.model.ArticleClassify;
import com.sep.content.repository.ArticleClassifyMapper;
import com.sep.content.service.ArticleClassifyService;
import com.sep.content.vo.ArticleClassifyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章分类表 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class ArticleClassifyServiceImpl extends ServiceImpl<ArticleClassifyMapper, ArticleClassify> implements ArticleClassifyService {

//    @Value("${defaultclassifyname}")
//    private String defaultClassifyName;

    @Value("${defaultArticleClassifyId}")
    private Integer defaultArticleClassifyId;

    @Override
    public Integer addArticleClassify(AddArticleClassifyDto addArticleClassifyDto) {

        List<ArticleClassify> list = lambdaQuery().eq(ArticleClassify::getClassifyName, addArticleClassifyDto.getClassifyName()).list();
        if (list != null && list.size() > 1)
            throw new SepCustomException(BizErrorCode.EXISTS);
        Integer sortNum = lambdaQuery().eq(ArticleClassify::getSort, addArticleClassifyDto.getSort()).count();
        if ((addArticleClassifyDto.getId() == null || addArticleClassifyDto.getId().equals(0)) && sortNum > 0) {
            throw new SepCustomException(BizErrorCode.SORT_ERROR);
        }

        ArticleClassify articleClassify = new ArticleClassify();
        BeanUtils.copyProperties(addArticleClassifyDto, articleClassify);
        if (articleClassify.getId() != null && articleClassify.getId() > 0) {
            return articleClassify.updateById() ? 1 : 0;
        } else {
            return articleClassify.insert() ? 1 : 0;
        }
    }

    @Override
    public Integer updateArticleClassifyStatus(UpdateStatusDto updateStatusDto) {
        ArticleClassify articleClassify = getById(updateStatusDto.getId());
        if (articleClassify != null) {
            articleClassify.setUpDownStatus(updateStatusDto.getStatus());
            return articleClassify.updateById() ? 1 : 0;
        }
        return 0;
    }

    @Override
    public Integer UpdateArticleClassifySort(UpdateArticleClassifySortDto updateArticleClassifySortDto) {
        ArticleClassify articleClassify = getById(updateArticleClassifySortDto.getId());
        if (articleClassify != null) {
            articleClassify.setSort(updateArticleClassifySortDto.getSort());
            return articleClassify.updateById() ? 1 : 0;
        }
        return 0;
    }

    @Override
    public List<ArticleClassifyVo> getArticleClassifysBack() {

        List<ArticleClassify> list = lambdaQuery()
                .orderByAsc(ArticleClassify::getSort).orderByDesc(ArticleClassify::getCreateTime).list();
        if (list != null && list.size() > 0) {
            List<ArticleClassifyVo> result = list.stream().map(e -> {
                ArticleClassifyVo vo = new ArticleClassifyVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public List<ArticleClassifyVo> getArticleClassifysXcx(Integer top) {
        List<ArticleClassify> list = new ArrayList<>();
        if (top != null && top > 0) {
            list = lambdaQuery().eq(ArticleClassify::getUpDownStatus, CommonStatus.YES.getCode())
                    .orderByAsc(ArticleClassify::getSort).orderByDesc(ArticleClassify::getCreateTime).last("limit 0," + top).list();
        } else {
            list = lambdaQuery().eq(ArticleClassify::getUpDownStatus, CommonStatus.YES.getCode())
                    .orderByAsc(ArticleClassify::getSort).orderByDesc(ArticleClassify::getCreateTime).list();
        }
        if (list != null && list.size() > 0) {
            List<ArticleClassifyVo> result = list.stream().map(e -> {
                ArticleClassifyVo vo = new ArticleClassifyVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
//            if (top != null && top > 0) {
//                ArticleClassifyVo homeVo = new ArticleClassifyVo();
//                homeVo.setId(0);
//                homeVo.setClassifyName(defaultClassifyName);
//                result.add(0, homeVo);
//            }
            return result;
        }
        return null;
    }

    @Override
    public List<ArticleClassifyVo> getArticleClassifysXcxForRedhouse() {
        List<ArticleClassifyVo> result=new ArrayList<>();
//        ArticleClassifyVo homeVo = new ArticleClassifyVo();
//        homeVo.setId(0);
//        homeVo.setClassifyName(defaultClassifyName);
//        result.add(0, homeVo);
        ArticleClassify articleClassify=getById(defaultArticleClassifyId);
        if(articleClassify!=null){
            ArticleClassifyVo vo = new ArticleClassifyVo();
            BeanUtils.copyProperties(articleClassify, vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public ArticleClassifyVo getArticleClassify(IdDto idDto) {
        ArticleClassify articleClassify = getById(idDto.getId());
        if (articleClassify != null) {
            ArticleClassifyVo vo = new ArticleClassifyVo();
            BeanUtils.copyProperties(articleClassify, vo);
            return vo;
        }
        return null;
    }

    @Override
    public Integer subtractArticleNum(IdDto idDto) {
        ArticleClassify articleClassify = getById(idDto.getId());
        if (articleClassify != null && articleClassify.getArticleNum()>0) {
            articleClassify.setArticleNum(articleClassify.getArticleNum() - 1);
            return articleClassify.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusArticleNum(IdDto idDto) {
        ArticleClassify articleClassify = getById(idDto.getId());
        if (articleClassify != null) {
            articleClassify.setArticleNum(articleClassify.getArticleNum() + 1);
            return articleClassify.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer delArticleClassify(IdDto idDto) {
        ArticleClassify articleClassify = getById(idDto.getId());
        if (articleClassify != null) {
            if (articleClassify.getArticleNum() > 0)
                throw new SepCustomException(BizErrorCode.NOT_DEL);
            return articleClassify.deleteById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<ArticleClassify> getArticleClassifys(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {

            return lambdaQuery().in(ArticleClassify::getId, ids).list();

        }
        return null;
    }
}
