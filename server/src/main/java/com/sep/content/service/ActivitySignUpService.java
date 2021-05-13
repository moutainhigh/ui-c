package com.sep.content.service;

import com.sep.content.dto.ActivitySignUpDto;
import com.sep.content.model.ActivitySignUp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.vo.ActivitySignUpVo;

import java.util.List;

/**
 * <p>
 * 用户填写项表  服务类
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-14
 */
public interface ActivitySignUpService extends IService<ActivitySignUp> {

    Boolean add(Integer id, List<ActivitySignUpDto> activitySignUp);


    List<ActivitySignUpVo> get(Integer id);
}
