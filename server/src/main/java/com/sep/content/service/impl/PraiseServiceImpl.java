package com.sep.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.AddPraiseDto;
import com.sep.content.dto.IdDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.enums.ObjType;
import com.sep.content.model.Praise;
import com.sep.content.repository.PraiseMapper;
import com.sep.content.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseMapper, Praise> implements PraiseService {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private IncreaseIntegral increaseIntegral;



    @Override
    public Integer addPraise(AddPraiseDto addPraiseDto) {
        Integer result = null;
        String userId = JwtUtils.parseJWT(addPraiseDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Praise praise = new Praise();
            BeanUtils.copyProperties(addPraiseDto, praise);
            praise.setUserId(Integer.parseInt(userId));
            Integer isPraise = lambdaQuery().eq(Praise::getUserId, praise.getUserId())
                    .eq(Praise::getObjType, praise.getObjType())
                    .eq(Praise::getObjId, praise.getObjId()).count();
            if (isPraise > 0) {
                return result;
            } else {
                result = praise.insert() ? 1 : 0;
                if (result > 0) {
                    IdDto id = new IdDto();
                    id.setId(praise.getObjId());
                    if (praise.getObjType().equals(ObjType.ACTIVITY.getCode())) {
                        activityService.plusPraise(id);
                    }
                    if (praise.getObjType().equals(ObjType.ARTICLE.getCode())) {
                        articleService.plusPraise(id);

                    }
                    if (praise.getObjType().equals(ObjType.COMMENT.getCode())) {
                        commentService.plusPraise(id);
                    }
                    increaseIntegral.increase(Integer.parseInt(userId),3);
                }
            }
        }

        return result;
    }

    @Override
    public Integer delPraise(AddPraiseDto addPraiseDto) {
        Integer result = null;
        String userId = JwtUtils.parseJWT(addPraiseDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            List<Praise> lsit = lambdaQuery().eq(Praise::getUserId, Integer.parseInt(userId))
                    .eq(Praise::getObjType, addPraiseDto.getObjType())
                    .eq(Praise::getObjId, addPraiseDto.getObjId()).list();
            if (lsit != null && lsit.size() > 0) {
                Praise praise = lsit.get(0);
                result = praise.deleteById() ? 1 : 0;
                if (result > 0) {
                    IdDto id = new IdDto();
                    id.setId(praise.getObjId());
                    if (praise.getObjType().equals(ObjType.ACTIVITY.getCode())) {
                        activityService.subtracPraise(id);
                    }
                    if (praise.getObjType().equals(ObjType.ARTICLE.getCode())) {
                        articleService.subtracPraise(id);

                    }
                    if (praise.getObjType().equals(ObjType.COMMENT.getCode())) {
                        commentService.subtracPraise(id);
                    }
                }
            }

        }

        return result;
    }

    @Override
    public Integer isPraise(AddPraiseDto addPraiseDto) {
        String userId = JwtUtils.parseJWT(addPraiseDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            return lambdaQuery().eq(Praise::getUserId, Integer.parseInt(userId))
                    .eq(Praise::getObjType, addPraiseDto.getObjType())
                    .eq(Praise::getObjId, addPraiseDto.getObjId()).count();
        } else {
            throw new SepCustomException(BizErrorCode.EXISTS);
        }
    }
}
