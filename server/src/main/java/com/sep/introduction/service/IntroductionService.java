package com.sep.introduction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.introduction.dto.IntroductionDto;
import com.sep.introduction.model.Introduction;
import com.sep.introduction.vo.IntroductionVo;

/**
 * <p>
 *   服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-24
 */
public interface IntroductionService extends IService<Introduction> {

    IntroductionVo get();

    Integer add(IntroductionDto introductionDto);
}
