package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.AddEnterDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchEnterDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.Activity;
import com.sep.content.model.ActivityEnter;
import com.sep.content.repository.ActivityEnterMapper;
import com.sep.content.service.ActivityEnterService;
import com.sep.content.service.ActivityService;
import com.sep.content.service.IncreaseIntegral;
import com.sep.content.vo.ActivityEnterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-09
 */
@Service
public class ActivityEnterServiceImpl extends ServiceImpl<ActivityEnterMapper, ActivityEnter> implements ActivityEnterService {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private IncreaseIntegral increaseIntegral;


    @Override
    public Integer enter(AddEnterDto addEnterDto) {

        String userId = JwtUtils.parseJWT(addEnterDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            Integer currentNum = activityService.getCurrentNum(addEnterDto.getActivityId());
            if (currentNum == 0) {
                throw new SepCustomException(BizErrorCode.ACTIVITYENTER_ERROR);
            }
            ActivityEnter enter = new ActivityEnter();
            BeanUtils.copyProperties(addEnterDto, enter);
            enter.setUserId(Integer.parseInt(userId));
            Boolean result = enter.insert();
            if (result) {
                IdDto id = new IdDto();
                id.setId(addEnterDto.getActivityId());
                activityService.subtractNum(id);
                increaseIntegral.increase(enter.getUserId(), 8);
            }
            return result ? 1 : 0;
        }

        return null;
    }

    @Override
    public IPage<ActivityEnterVo> searchActivityEnter(SearchEnterDto searchEnterDto) {

        IPage<ActivityEnterVo> result = new Page<>();
        Page<ActivityEnter> page = new Page<>(searchEnterDto.getCurrentPage(), searchEnterDto.getPageSize());
        final List<Integer> ids = activityService.getIds(searchEnterDto.getActivityName());
        if (StringUtils.isNotBlank(searchEnterDto.getActivityName()) && (ids == null || ids.size() == 0))
            return result;
        IPage<ActivityEnter> data = baseMapper.selectPage(page, new LambdaQueryWrapper<ActivityEnter>()
                .eq((searchEnterDto.getUserId() != null && searchEnterDto.getUserId() > 0), ActivityEnter::getUserId, searchEnterDto.getUserId())
                .in((ids != null && ids.size() > 0), ActivityEnter::getActivityId, ids)
                .eq((searchEnterDto.getIsApply() != null), ActivityEnter::getIsApply, searchEnterDto.getIsApply())
                .orderByDesc(ActivityEnter::getCreateTime)
        );
        if (data != null && data.getRecords() != null) {
            List<ActivityEnterVo> list = data.getRecords().stream().map(e -> {
                ActivityEnterVo vo = new ActivityEnterVo();
                BeanUtils.copyProperties(e, vo);
                Activity activity = activityService.getById(e.getActivityId());
                if (activity != null) {
                    vo.setActivityName(activity.getTitle());
                }
                return vo;
            }).collect(Collectors.toList());
            result.setPages(data.getPages());
            result.setCurrent(data.getCurrent());
            result.setTotal(data.getTotal());
            result.setRecords(list);
        }
        return result;

    }

    @Override
    public List<Integer> getActivityIds(Integer userId) {
        return lambdaQuery().eq(ActivityEnter::getUserId, userId).list().stream().map(e -> {
            return e.getActivityId();
        }).collect(Collectors.toList());
    }
}
