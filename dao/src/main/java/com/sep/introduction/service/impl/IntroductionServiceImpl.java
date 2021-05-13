package com.sep.introduction.service.impl;

import com.sep.introduction.model.Introduction;
import com.sep.introduction.repository.IntroductionMapper;
import com.sep.introduction.service.IntroductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-24
 */
@Service
public class IntroductionServiceImpl extends ServiceImpl<IntroductionMapper, Introduction> implements IntroductionService {

}
