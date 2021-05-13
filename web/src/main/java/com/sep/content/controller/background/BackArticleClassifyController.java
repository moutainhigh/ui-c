package com.sep.content.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddArticleClassifyDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateArticleClassifySortDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.service.ArticleClassifyService;
import com.sep.content.vo.ArticleClassifyVo;
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
 * 文章分类表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/article-classify")
@Api(value = "管理后台资讯分类API", tags = {"管理后台资讯分类API"})
public class BackArticleClassifyController {


    @Autowired
    private ArticleClassifyService articleClassifyService;


    @PostMapping(value = "/addArticleClassify")
    @ApiOperation(value = " 添加资讯分类", httpMethod = "POST")
    public ResponseData<Integer> addArticleClassify(@RequestBody @Valid AddArticleClassifyDto addArticleClassifyDto) {
        return ResponseData.OK(articleClassifyService.addArticleClassify(addArticleClassifyDto));
    }

    @PostMapping(value = "/getArticleClassifysBack")
    @ApiOperation(value = "资讯分类列表", httpMethod = "POST")
    public ResponseData<List<ArticleClassifyVo>> getArticleClassifysBack() {
        return ResponseData.OK(articleClassifyService.getArticleClassifysBack());
    }

    @PostMapping(value = "/getArticleClassify")
    @ApiOperation(value = "获取一条资讯分类", httpMethod = "POST")
    public ResponseData<ArticleClassifyVo> getArticleClassify(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(articleClassifyService.getArticleClassify(idDto));
    }

    @PostMapping(value = "/updateArticleClassifyStatus")
    @ApiOperation(value = " 更新资讯状态", httpMethod = "POST")
    public ResponseData<Integer> updateArticleClassifyStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        return ResponseData.OK(articleClassifyService.updateArticleClassifyStatus(updateStatusDto));
    }

    @PostMapping(value = "/UpdateArticleClassifySort")
    @ApiOperation(value = " 更新资讯排序", httpMethod = "POST")
    public ResponseData<Integer> UpdateArticleClassifySort(@RequestBody @Valid UpdateArticleClassifySortDto updateArticleClassifySortDto) {
        return ResponseData.OK(articleClassifyService.UpdateArticleClassifySort(updateArticleClassifySortDto));
    }

    @PostMapping(value = "/delArticleClassify")
    @ApiOperation(value = "删除资讯", httpMethod = "POST")
    public ResponseData<Integer> delArticleClassify(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(articleClassifyService.delArticleClassify(idDto));
    }


}
