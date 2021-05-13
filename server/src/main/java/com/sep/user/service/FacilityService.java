package com.sep.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.user.model.Facility;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-06-02
 */
public interface FacilityService extends IService<Facility> {

    default List<Facility> list(Integer userId) {
        return lambdaQuery().eq(Facility::getUserId, userId).orderByDesc(Facility::getId).last("limit 0,10").list();
    }
}
