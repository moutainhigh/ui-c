package com.sep.content.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchCommentDto;
import com.sep.content.dto.UpdateStatusDto;
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

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/comment")
@Api(value = "管理后台评论相关API", tags = {"管理后台评论相关API"})
public class BackCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/searchComment")
    @ApiOperation(value = "分页查询评论", httpMethod = "POST")
    public ResponseData<IPage<CommentVo>> searchComment(@RequestBody SearchCommentDto searchCommentDto) {
        return ResponseData.OK(commentService.searchComment(searchCommentDto));
    }


    @PostMapping(value = "/updateCommentStatus")
    @ApiOperation(value = "更新评论状态", httpMethod = "POST")
    public ResponseData<Integer> updateCommentStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        return ResponseData.OK(commentService.updateCommentStatus(updateStatusDto));
    }

    @PostMapping(value = "/delComment")
    @ApiOperation(value = "删除评论", httpMethod = "POST")
    public ResponseData<Integer> delComment(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(commentService.delComment(idDto));
    }


}
