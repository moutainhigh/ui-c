package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddCommentDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchCommentDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.content.model.Comment;
import com.sep.content.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface CommentService extends IService<Comment> {


    Integer addAddCommentDto(AddCommentDto addCommentDto);

    List<CommentVo> getCommentTop5(SearchCommentDto searchCommentDto);

    Integer countComment(SearchCommentDto searchCommentDto);

    Integer updateCommentStatus(UpdateStatusDto updateStatusDto);

    Integer delComment(IdDto idDto);

    /**
     * 通用接口
     * 评论
     */
    IPage<CommentVo> searchComment(SearchCommentDto searchCommentDto);


    List<CommentVo> getList(Integer skuId);

    /**
     * 内部接口
     * <p>
     * 调用一次 点赞数量加一
     */
    Integer plusPraise(IdDto idDto);


    /**
     * 内部接口
     * <p>
     * 调用一次 点赞数量加一
     */
    Integer subtracPraise(IdDto idDto);


}
