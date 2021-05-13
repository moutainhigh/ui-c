package com.sep.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.JwtUtils;
import com.sep.common.utils.LocalDateTimeUtils;
import com.sep.content.dto.AddActivityDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchActivityDto;
import com.sep.content.enums.ActivityStatus;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.Activity;
import com.sep.content.model.ActivitySignUp;
import com.sep.content.repository.ActivityMapper;
import com.sep.content.service.ActivityEnterService;
import com.sep.content.service.ActivityService;
import com.sep.content.service.ActivitySignUpService;
import com.sep.content.vo.ActivitySignUpVo;
import com.sep.content.vo.ActivityVo;
import com.sep.sku.dto.BatchSearchSkuInfoDto;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SkuInfoRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private ActivityEnterService activityEnterService;
    @Autowired
    private ActivitySignUpService activitySignUpService;

    @Override
    public Integer addActivity(AddActivityDto addActivityDto) {
        LocalDateTime start = LocalDateTimeUtils.localTime(addActivityDto.getStartTime());
        LocalDateTime end = LocalDateTimeUtils.localTime(addActivityDto.getEndTime());
        LocalDateTime current = LocalDateTime.now();
        if (start.isAfter(end))
            throw new SepCustomException(BizErrorCode.TIME_ERROR);
        if (start.equals(end))
            throw new SepCustomException(BizErrorCode.TIME_ERROR);
        if (start.isBefore(current))
            throw new SepCustomException(BizErrorCode.TIME_ERROR);
        Activity activity = new Activity();
        BeanUtils.copyProperties(addActivityDto, activity);
        activity.setStartTime(start);
        activity.setEndTime(end);
        activity.setCurrentNum(addActivityDto.getNum());

        if (StringUtils.isNotBlank(activity.getSkuIds())) {
            String[] skuIds = activity.getSkuIds().split(",");
            if (skuIds != null && skuIds.length > 0) {
                for (String skuId : skuIds) {
                    if (StringUtils.isNotBlank(skuId)) {
                        if (!skuInfoService.isSkuOnlineStatus(Integer.parseInt(skuId))) {
                            throw new SepCustomException(BizErrorCode.NOTNULL_SKU);

                        }
                    }
                }
            }


        }
        if (start.equals(current)) {
            activity.setStatus(ActivityStatus.ING.getCode());
        } else {
            activity.setStatus(ActivityStatus.Not_Beginning.getCode());
        }
        if (activity.getId() != null && activity.getId() > 0) {
            Activity a = getById(activity.getId());
            if (a.getStatus().equals(ActivityStatus.ACCOMPLISH.getCode()))
                throw new SepCustomException(BizErrorCode.COMPLETE);
            boolean b = activity.updateById();
            if (b && addActivityDto.getActivitySignUp() != null && addActivityDto.getActivitySignUp().size() != 0) {
                activitySignUpService.add(activity.getId(), addActivityDto.getActivitySignUp());
            }
            return b ? 1 : 0;
        } else {
            boolean insert = activity.insert();
            if (insert && addActivityDto.getActivitySignUp() != null && addActivityDto.getActivitySignUp().size() != 0) {
                activitySignUpService.add(activity.getId(), addActivityDto.getActivitySignUp());
            }
            return insert ? 1 : 0;
        }
    }

    @Override
    public ActivityVo getActivity(IdDto idDto, Boolean isReturnSku) {
        Activity activity = getById(idDto.getId());
        List<ActivitySignUp> list = activitySignUpService.lambdaQuery().eq(ActivitySignUp::getActivityId, idDto.getId()).list();
        if (activity != null) {
            ActivityVo result = new ActivityVo();
            BeanUtils.copyProperties(activity, result);
            if(list!=null){
                result.setActivitySignUpVos(list.stream().map(e->{
                    ActivitySignUpVo activitySignUpVo=new ActivitySignUpVo();
                    activitySignUpVo.setName(e.getName());
                    activitySignUpVo.setId(e.getId());
                    return activitySignUpVo;
                }).collect(Collectors.toList()));
            }
            result.setStartTimeStr(LocalDateTimeUtils.localTimeStr(activity.getStartTime()));
            result.setEndTimeStr(LocalDateTimeUtils.localTimeStr(activity.getEndTime()));
            if (isReturnSku) {
                if (StringUtils.isNotBlank(activity.getSkuIds())) {
                    List<Integer> skuIds = Arrays.asList(activity.getSkuIds().split(",")).stream().map(e -> {
                        if (StringUtils.isNotBlank(e)) {
                            return Integer.parseInt(e);
                        }
                        return 0;
                    }).collect(Collectors.toList());

                    BatchSearchSkuInfoDto skuInfoDto = new BatchSearchSkuInfoDto();
                    skuInfoDto.setSkuIdList(skuIds);
                    List<SkuInfoRespVo> skus = skuInfoService.getSkuListByIds(skuInfoDto);
                    if (skus != null && skus.size() > 0) {
                        result.setSkus(skus);
                    }
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public Integer delActivity(IdDto idDto) {
        Activity activity = getById(idDto.getId());
        if (activity.getNum() > activity.getCurrentNum())
            throw new SepCustomException(BizErrorCode.NOT_DEL);
//        if (activity.getPraiseNum() > 0)
//            throw new SepCustomException(BizErrorCode.NOT_DEL);
        return activity.deleteById() ? 1 : 0;
    }

    @Override
    public IPage<ActivityVo> searchActivity(SearchActivityDto searchActivityDto) {
        IPage<ActivityVo> result = new Page<>();
        Page<Activity> page = new Page<>(searchActivityDto.getCurrentPage(), searchActivityDto.getPageSize());
        IPage<Activity> data = null;
        if (StringUtils.isNotBlank(searchActivityDto.getToken())) {
            String userId = JwtUtils.parseJWT(searchActivityDto.getToken()).get("id").toString();
            final List<Integer> ids = activityEnterService.getActivityIds(Integer.parseInt(userId));
            data = baseMapper.selectPage(page, new LambdaQueryWrapper<Activity>().in((ids != null && ids.size() > 0), Activity::getId, ids)
                    .like((StringUtils.isNotBlank(searchActivityDto.getTitle())), Activity::getTitle, searchActivityDto.getTitle())
                    .eq((searchActivityDto.getStatus() != null && searchActivityDto.getStatus() > 0), Activity::getStatus, searchActivityDto.getStatus()).orderByDesc(Activity::getCreateTime));
        } else {
            data = baseMapper.selectPage(page, new LambdaQueryWrapper<Activity>()
                    .like((StringUtils.isNotBlank(searchActivityDto.getTitle())), Activity::getTitle, searchActivityDto.getTitle())
                    .eq((searchActivityDto.getStatus() != null && searchActivityDto.getStatus() > 0), Activity::getStatus, searchActivityDto.getStatus()).orderByDesc(Activity::getCreateTime));
        }

        if (data != null && data.getRecords() != null) {
            List<ActivityVo> list = data.getRecords().stream().map(e -> {
                ActivityVo vo = new ActivityVo();
                BeanUtils.copyProperties(e, vo);
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
    public List<ActivityVo> getHomeActivityVo() {
        List<Activity> list = lambdaQuery().eq(Activity::getStatus, ActivityStatus.Not_Beginning.getCode()).orderByDesc(Activity::getCreateTime).last("limit 0,5").list();
        if (list != null && list.size() > 0) {
            return list.stream().map(e -> {
                ActivityVo vo = new ActivityVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Integer subtractNum(IdDto idDto) {
        Activity activity = getById(idDto.getId());
        if (activity != null) {
            if (activity.getCurrentNum() > 0) {
                activity.setCurrentNum(activity.getCurrentNum() - 1);
                return activity.updateById() ? 1 : 0;
            }
        }
        return null;
    }

    @Override
    public Integer plusRetransmission(IdDto idDto) {
        Activity activity = getById(idDto.getId());
        if (activity != null) {
            activity.setRetransmissionNum(activity.getRetransmissionNum() + 1);
            return activity.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer plusPraise(IdDto idDto) {
        Activity activity = getById(idDto.getId());
        if (activity != null) {
            activity.setPraiseNum(activity.getPraiseNum() + 1);
            return activity.updateById() ? 1 : 0;
        }

        return null;
    }

    @Override
    public Integer subtracPraise(IdDto idDto) {
        Activity activity = getById(idDto.getId());
        if (activity != null) {
            activity.setPraiseNum(activity.getPraiseNum() - 1);
            return activity.updateById() ? 1 : 0;
        }
        return null;
    }

    @Override
    public List<Integer> getIds(String title) {
        if ((StringUtils.isNotBlank(title)))
            return lambdaQuery().like(Activity::getTitle, title).list().stream().map(e -> e.getId()).collect(Collectors.toList());
        return null;
    }

    @Override
    public void updateActivityStatus() {
        LocalDateTime current = LocalDateTime.now();
        List<Activity> list = lambdaQuery().list();
        for (Activity item : list) {
            if (LocalDateTime.now().isBefore(item.getStartTime())) {
                item.setStatus(ActivityStatus.Not_Beginning.getCode());
            } else if (item.getStartTime().isBefore(LocalDateTime.now())
                    && item.getEndTime().isAfter(LocalDateTime.now())) {
                item.setStatus(ActivityStatus.ING.getCode());
                //如果已经预约,并且当前时间大于上课开始时间小于上课结束时间,显示在上课
            } else {
                item.setStatus(ActivityStatus.ACCOMPLISH.getCode());
                //如果已经预约,并且当前时间大于上课结束时间,显示已结束
            }
            item.updateById();
        }

    }

    @Override
    public Boolean isActivityOneLine(Integer id) {
        Activity activit = getById(id);
        if (activit == null)
            return false;
        if (activit.getStatus().equals(ActivityStatus.ACCOMPLISH.getCode()))
            return false;
        return true;
    }

    @Override
    public Integer getCurrentNum(Integer id) {
        Activity activity = getById(id);
        return activity.getCurrentNum();
    }
}
