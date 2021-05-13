package com.sep.content.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddArticleDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchArticleDto;
import com.sep.content.dto.UpdateStatusDto;
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

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/article")
@Api(value = "管理后台资讯API", tags = {"管理后台资讯API"})
public class BackArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/addAddArticle")
    @ApiOperation(value = "添加资讯", httpMethod = "POST")
    public ResponseData<Integer> addAddArticle(@RequestBody @Valid AddArticleDto addArticleDto) {
        return ResponseData.OK(articleService.addAddArticle(addArticleDto));
    }

    @PostMapping(value = "/searchArticle")
    @ApiOperation(value = "分页查询资讯", httpMethod = "POST")
    public ResponseData<IPage<ArticleVo>> searchArticle(@RequestBody SearchArticleDto searchArticleDto) {
        return ResponseData.OK(articleService.searchArticle(searchArticleDto));
    }

    @PostMapping(value = "/getArticle")
    @ApiOperation(value = "查询资讯详情", httpMethod = "POST")
    public ResponseData<ArticleVo> getArticle(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(articleService.getArticle(idDto,  false));
    }

    @PostMapping(value = "/delArticle")
    @ApiOperation(value = "删除资讯", httpMethod = "POST")
    public ResponseData<Integer> delArticle(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(articleService.delArticle(idDto));
    }

    @PostMapping(value = "/updateArticleUpDownStatus")
    @ApiOperation(value = "更新资讯状态", httpMethod = "POST")
    public ResponseData<Integer> updateArticleUpDownStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        return ResponseData.OK(articleService.updateArticleUpDownStatus(updateStatusDto));
    }

}
