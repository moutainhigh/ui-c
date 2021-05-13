package com.sep.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddArticleClassifyDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateArticleClassifySortDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.model.ArticleClassify;
import com.sep.content.vo.ArticleClassifyVo;

import java.util.List;

/**
 * <p>
 * 文章分类表 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface ArticleClassifyService extends IService<ArticleClassify> {

    /**
     * 管理后台接口
     * 添加资讯分类
     */
    Integer addArticleClassify(AddArticleClassifyDto addArticleClassifyDto);

    /**
     * 管理后台接口
     * 更新资讯状态
     */
    Integer updateArticleClassifyStatus(UpdateStatusDto updateStatusDto);

    /**
     * 管理后台接口
     * 更新资讯排序
     */
    Integer UpdateArticleClassifySort(UpdateArticleClassifySortDto updateArticleClassifySortDto);

    /**
     * 管理后台接口
     * 查询全部资讯  分类
     */
    List<ArticleClassifyVo> getArticleClassifysBack();

    /**
     * 小程序接口
     * 查询资讯分类
     */
    List<ArticleClassifyVo> getArticleClassifysXcx(Integer top);

    /**
     * 红房子首页分类

     * */
    List<ArticleClassifyVo> getArticleClassifysXcxForRedhouse();

    /**
     * 管理后台接口
     * 获取一条资讯
     */
    ArticleClassifyVo getArticleClassify(IdDto idDto);

    /**
     * 内部接口
     * 减文章数量
     */
    Integer subtractArticleNum(IdDto idDto);

    /**
     * 内部接口
     * 加文章数量
     */
    Integer plusArticleNum(IdDto idDto);

    /**
     * 管理后台接口
     * 删除分类
     */
    Integer delArticleClassify(IdDto idDto);

    /**
     * 内部接口
     */
    List<ArticleClassify> getArticleClassifys(List<Integer> ids);


}
