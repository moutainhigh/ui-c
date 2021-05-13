package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddArticleDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchArticleDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.model.Article;
import com.sep.content.vo.ArticleVo;

import java.util.List;

/**
 * <p>
 * 资讯表 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface ArticleService extends IService<Article> {


    Integer addAddArticle(AddArticleDto addArticleDto);

    /**
     * 通用接口 查询资讯
     * 查询全部资讯
     */
    IPage<ArticleVo> searchArticle(SearchArticleDto searchArticleDto);

    /**
     *  小程序接口
     * 查询首页资讯
     */
    List<ArticleVo> getHomeArticleTop3(SearchArticleDto searchArticleDto);


    ArticleVo getArticle(IdDto idDto, Boolean isReturnSkus);

    Integer delArticle(IdDto idDto);

    Integer updateArticleUpDownStatus(UpdateStatusDto updateStatusDto);

    /**
     * 内部接口
     *
     * 调用一次 加评论
     */
    Integer  plusComment(IdDto idDto);



    /**
     * 内部接口
     *
     * 调用一次 加评论
     */
    Integer  subtracComment(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer plusPraise(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer plusRetransmissionNum(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer subtracPraise(IdDto idDto);

    /**
     * 内部接口
     *
     * ids查询
     */
    List<ArticleVo> getArticleVos(List<Integer> ids);

    Boolean isArticleOneline(Integer id);


}
