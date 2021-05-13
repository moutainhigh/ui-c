package com.sep.user.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sep.user.model.Address;
import org.apache.ibatis.annotations.Param;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
public interface AddressMapper extends BaseMapper<Address> {

    Set<Integer> maxIds(@Param(Constants.WRAPPER) Wrapper wrapper);

}
