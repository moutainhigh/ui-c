package com.sep.content.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddCommentDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchCommentDto;
import com.sep.content.service.CommentService;
import com.sep.content.vo.CommentVo;
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
 * 评论表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/xcx/comment")
@Api(value = "小程序评论相关API", tags = {"小程序评论相关API"})
public class XcxCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/searchComment")
    @ApiOperation(value = "分页查询评论", httpMethod = "POST")
    public ResponseData<IPage<CommentVo>> searchComment(@RequestBody SearchCommentDto searchCommentDto) {
        return ResponseData.OK(commentService.searchComment(searchCommentDto));
    }

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询商全部评论", httpMethod = "POST")
    public ResponseData<List<CommentVo>> getList(@RequestBody IdDto idDto) {
        return ResponseData.OK(commentService.getList(idDto.getId()));
    }

    @PostMapping(value = "/addAddCommentDto")
    @ApiOperation(value = "添加评论", httpMethod = "POST")
    public ResponseData<Integer> addAddCommentDto(@RequestBody @Valid AddCommentDto addCommentDto) {
        return ResponseData.OK(commentService.addAddCommentDto(addCommentDto));
    }

    @PostMapping(value = "/getCommentTop5")
    @ApiOperation(value = "获取前5条评论", httpMethod = "POST")
    public ResponseData<List<CommentVo>> getCommentTop5(@RequestBody SearchCommentDto searchCommentDto) {
        return ResponseData.OK(commentService.getCommentTop5(searchCommentDto));
    }


}
