package com.sep.user.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sep.user.model.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tianyu
 * @since 2020-01-08
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

    List<Map<String, Number>> countLower1Number(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<Map<String, Number>> countLower2Number(@Param(Constants.WRAPPER) Wrapper wrapper);

}
