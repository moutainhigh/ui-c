package com.sep.content.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.TopDto;
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
@RequestMapping("/xcx/article-classify")
@Api(value = "小程序资讯分类 API", tags = {"小程序资讯分类 API"})
public class XcxArticleClassifyController {


    @Autowired
    private ArticleClassifyService articleClassifyService;


    @PostMapping(value = "/getArticleClassifysXcx")
    @ApiOperation(value = " 获取资讯分类列表", httpMethod = "POST")
    public ResponseData<List<ArticleClassifyVo>> getArticleClassifysXcx(@RequestBody TopDto topDto) {
        return ResponseData.OK(articleClassifyService.getArticleClassifysXcx(topDto.getTop()));
    }

    @PostMapping(value = "/getArticleClassifysXcxForRedhouse")
    @ApiOperation(value = "获取红房子首页分类", httpMethod = "POST")
    public ResponseData<List<ArticleClassifyVo>> getArticleClassifysXcxForRedhouse() {
        return ResponseData.OK(articleClassifyService.getArticleClassifysXcxForRedhouse());
    }


}
