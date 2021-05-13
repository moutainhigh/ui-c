package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.content.dto.AddEnterDto;
import com.sep.content.dto.SearchEnterDto;
import com.sep.content.dto.SearchEnterSignUpDto;
import com.sep.content.model.ActivityEnterSignUp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.vo.ActivityEnterSignUpVo;
import com.sep.content.vo.ActivityEnterSignUpXcxVo;
import com.sep.content.vo.EnterRespVo;

import java.util.List;

/**
 * <p>
 * 用户填写项中间表  服务类
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-14
 */
public interface ActivityEnterSignUpService extends IService<ActivityEnterSignUp> {

    EnterRespVo enter(AddEnterDto addEnterDto);

    ActivityEnterSignUpVo searchActivityEnter(SearchEnterSignUpDto searchEnterDto);

    ActivityEnterSignUpXcxVo searchActivityXcxEnter(SearchEnterSignUpDto searchEnterSignUpDto);
}
