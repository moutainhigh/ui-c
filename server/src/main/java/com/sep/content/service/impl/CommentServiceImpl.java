package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.*;
import com.sep.content.enums.CommonStatus;
import com.sep.content.enums.ObjType;
import com.sep.content.model.Comment;
import com.sep.content.repository.CommentMapper;
import com.sep.content.service.ArticleService;
import com.sep.content.service.CommentService;
import com.sep.content.service.IncreaseIntegral;
import com.sep.content.service.PraiseService;
import com.sep.content.vo.ArticleVo;
import com.sep.content.vo.CommentVo;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private IncreaseIntegral increaseIntegral;


    @Override
    public Integer addAddCommentDto(AddCommentDto addCommentDto) {

        String userId = JwtUtils.parseJWT(addCommentDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Comment comment = new Comment();
            BeanUtils.copyProperties(addCommentDto, comment);
            comment.setUserId(Integer.parseInt(userId));
            return comment.insert() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<CommentVo> getCommentTop5(SearchCommentDto searchCommentDto) {
        List<Comment> list = lambdaQuery().eq(Comment::getObjType, searchCommentDto.getObjType())
                .eq(Comment::getObjId, searchCommentDto.getObjId())
                .eq(Comment::getStatus, CommonStatus.YES.getCode())
                .orderByDesc(Comment::getCreateTime).last("limit 0,2").list();
        if (list != null && list.size() > 0) {

            List<Integer> userIds = list.stream().map(e -> {
                return e.getUserId();
            }).collect(Collectors.toList());
            GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
            getUserByIdsInput.setUserIds(userIds);
            List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
            List<CommentVo> result = list.stream().map(e -> {
                CommentVo vo = new CommentVo();
                BeanUtils.copyProperties(e, vo);
                vo.setIsPraise(0);
                userOutputs.forEach(item -> {
                    if (item.getId().equals(vo.getUserId())) {
                        vo.setAvatarurl(item.getAvatarurl());
                        vo.setNickname(item.getNickname());
                    }
                });
                if (StringUtils.isNotBlank(searchCommentDto.getToken())) {
                    AddPraiseDto addPraiseDto = new AddPraiseDto();
                    addPraiseDto.setToken(searchCommentDto.getToken());
                    addPraiseDto.setObjType(ObjType.COMMENT.getCode());
                    addPraiseDto.setObjId(vo.getId());
                    vo.setIsPraise(praiseService.isPraise(addPraiseDto));

                }
                return vo;

            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public Integer countComment(SearchCommentDto searchCommentDto) {

        return lambdaQuery().eq(Comment::getObjType, searchCommentDto.getObjType())
                .eq(Comment::getObjId, searchCommentDto.getObjId())
                .eq(Comment::getStatus, CommonStatus.YES.getCode()).count();
    }

    @Override
    public Integer updateCommentStatus(UpdateStatusDto updateStatusDto) {
        Integer result = null;
        Comment comment = getById(updateStatusDto.getId());
        if (comment != null) {
            comment.setStatus(updateStatusDto.getStatus());
            result = comment.updateById() ? 1 : 0;
            if (result > 0) {
                if (comment.getStatus().equals(CommonStatus.YES.getCode()) && comment.getObjType().equals(ObjType.ARTICLE.getCode())) {
                    IdDto id = new IdDto();
                    id.setId(comment.getObjId());
                    articleService.plusComment(id);
                    increaseIntegral.increase(comment.getUserId(), 5);
                }
            }
        }
        return result;
    }

    @Override
    public Integer delComment(IdDto idDto) {
        Integer result = null;
        //当删除可显示状态的评论 给资讯减评论数
        Comment comment = getById(idDto.getId());
        if (comment != null) {
            result = comment.deleteById() ? 1 : 0;
            if (result > 0 && comment.getStatus().equals(CommonStatus.YES.getCode()) && comment.getObjId().equals(ObjType.ARTICLE.getCode())) {
                IdDto id = new IdDto();
                id.setId(comment.getObjId());
                articleService.subtracComment(id);
            }
        }
        return result;
    }

    @Override
    public IPage<CommentVo> searchComment(SearchCommentDto searchCommentDto) {
        IPage<CommentVo> result = new Page<>();
        Page<Comment> page = new Page<>(searchCommentDto.getCurrentPage(), searchCommentDto.getPageSize());
        IPage<Comment> data = baseMapper.selectPage(page, new LambdaQueryWrapper<Comment>()
                .eq((searchCommentDto.getUserId() != null && searchCommentDto.getUserId() > 0), Comment::getUserId, searchCommentDto.getUserId())
                .eq(searchCommentDto.getObjType() != null, Comment::getObjType, searchCommentDto.getObjType())
                .eq((searchCommentDto.getObjId() != null && searchCommentDto.getObjId() > 0), Comment::getObjId, searchCommentDto.getObjId())
                .eq(searchCommentDto.getStatus() != null, Comment::getStatus, searchCommentDto.getStatus())
                .orderByDesc(Comment::getCommentPraiseNum)
                .orderByDesc(Comment::getCreateTime)
        );
        if (CollectionUtils.isEmpty(data.getRecords())) {
            return result;
        }
        List<Integer> userIds = data.getRecords().stream().map(Comment::getUserId).collect(Collectors.toList());
        List<Integer> obgids = data.getRecords().stream().map(Comment::getObjId).collect(Collectors.toList());
        GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
        getUserByIdsInput.setUserIds(userIds);
        List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
        List<CommentVo> list = data.getRecords().stream().map(e -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(e, vo);
            userOutputs.forEach(item -> {
                if (item.getId().equals(vo.getUserId())) {
                    vo.setAvatarurl(item.getAvatarurl());
                    vo.setNickname(item.getNickname());
                }
            });
            if (vo.getObjType().equals(ObjType.ARTICLE.getCode()) && vo.getObjId() > 0) {
                vo.setTitle(articleService.getById(vo.getObjId()).getTitle());
            }
            if (StringUtils.isNotBlank(searchCommentDto.getToken())) {
                AddPraiseDto addPraiseDto = new AddPraiseDto();
                addPraiseDto.setToken(searchCommentDto.getToken());
                addPraiseDto.setObjType(ObjType.COMMENT.getCode());
                addPraiseDto.setObjId(vo.getId());
                vo.setIsPraise(praiseService.isPraise(addPraiseDto));
            }
            return vo;
        }).collect(Collectors.toList());
        result.setPages(data.getPages());
        result.setCurrent(data.getCurrent());
        result.setTotal(data.getTotal());
        result.setRecords(list);
        return result;
    }

    @Override
    public List<CommentVo> getList(Integer skuId) {
        List<Comment> list = lambdaQuery().eq(Comment::getObjType, ObjType.SKU.getCode())
                .eq(Comment::getObjId, skuId)
                .eq(Comment::getStatus, CommonStatus.YES.getCode())
                .orderByDesc(Comment::getCreateTime).list();
        if (list != null && list.size() > 0) {
            List<Integer> userIds = list.stream().map(e -> {
                return e.getUserId();
            }).collect(Collectors.toList());
            GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
            getUserByIdsInput.setUserIds(userIds);
            List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
            List<CommentVo> result = list.stream().map(e -> {
                CommentVo vo = new CommentVo();
                BeanUtils.copyProperties(e, vo);
                userOutputs.forEach(item -> {
                    if (item.getId().equals(vo.getUserId())) {
                        vo.setAvatarurl(item.getAvatarurl());
                        vo.setNickname(item.getNickname());
                    }
                });
                return vo;
            }).collect(Collectors.toList());
            return result;
        }
        return null;
    }

    @Override
    public Integer plusPraise(IdDto idDto) {

        Comment comment = getById(idDto.getId());
        if (comment != null) {
            comment.setCommentPraiseNum(comment.getCommentPraiseNum() + 1);
            return comment.updateById() ? 1 : 0;
        }

        return null;
    }

    @Override
    public Integer subtracPraise(IdDto idDto) {
        Comment comment = getById(idDto.getId());
        if (comment != null) {
            comment.setCommentPraiseNum(comment.getCommentPraiseNum() - 1);
            return comment.updateById() ? 1 : 0;
        }

        return null;
    }
}
