package com.sep.content.service.impl;

import com.sep.content.dto.ActivitySignUpDto;
import com.sep.content.model.ActivitySignUp;
import com.sep.content.repository.ActivitySignUpMapper;
import com.sep.content.service.ActivitySignUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.content.vo.ActivitySignUpVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户填写项表  服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-14
 */
@Service
public class ActivitySignUpServiceImpl extends ServiceImpl<ActivitySignUpMapper, ActivitySignUp> implements ActivitySignUpService {

    @Override
    public Boolean add(Integer id, List<ActivitySignUpDto> activitySignUp) {
        activitySignUp.forEach(e -> {
            ActivitySignUp activitySignUp1 = new ActivitySignUp();
            activitySignUp1.setName(e.getName());
            activitySignUp1.setActivityId(id);
            if (e.getId() == null || e.getId() == 0) {
                activitySignUp1.setCreatedTime(LocalDateTime.now());
                activitySignUp1.insert();
            } else {
                activitySignUp1.setUpdatedTime(LocalDateTime.now());
                activitySignUp1.updateById();
            }
        });

        return true;
    }

    @Override
    public List<ActivitySignUpVo> get(Integer id) {
        List<ActivitySignUp> list = lambdaQuery().eq(ActivitySignUp::getActivityId, id).list();
        if (list != null && list.size() != 0) {
            List<ActivitySignUpVo> collect = new ArrayList<>();
            ActivitySignUpVo activitySignUpVo1 = new ActivitySignUpVo();
            activitySignUpVo1.setId(-1);
            activitySignUpVo1.setName("用户id");
            collect.add(activitySignUpVo1);
            activitySignUpVo1 = new ActivitySignUpVo();
            activitySignUpVo1.setId(-2);
            activitySignUpVo1.setName("创建时间");
            collect.add(activitySignUpVo1);
            collect.addAll(list.stream().map(e -> {
                ActivitySignUpVo activitySignUpVo = new ActivitySignUpVo();
                activitySignUpVo.setId(e.getId());
                activitySignUpVo.setName(e.getName());
                return activitySignUpVo;
            }).collect(Collectors.toList()));
            return collect;
        } else {
            return new ArrayList<ActivitySignUpVo>();
        }
    }
}
