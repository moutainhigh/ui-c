package com.sep.content.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchArticleDto;
import com.sep.content.service.ArticleService;
import com.sep.content.vo.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/xcx/article")
@Api(value = "小程序资讯API", tags = {"小程序资讯API"})
public class XcxArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping(value = "/searchArticle")
    @ApiOperation(value = "分页查询资讯", httpMethod = "POST")
    public ResponseData<IPage<ArticleVo>> searchArticle(@RequestBody SearchArticleDto searchArticleDto) {
        return ResponseData.OK(articleService.searchArticle(searchArticleDto));
    }

    @PostMapping(value = "/getArticle")
    @ApiOperation(value = "查询资讯详情", httpMethod = "POST")
    public ResponseData<ArticleVo> getArticle(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(articleService.getArticle(idDto, true));
    }

    @PostMapping(value = "/getHomeArticleTop3")
    @ApiOperation(value = "首页查询资讯", httpMethod = "POST")
    public ResponseData<List<ArticleVo>> getHomeArticleTop3(@RequestBody SearchArticleDto searchArticleDto) {
        return ResponseData.OK(articleService.getHomeArticleTop3(searchArticleDto));
    }


}
