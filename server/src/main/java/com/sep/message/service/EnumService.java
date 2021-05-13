package com.sep.message.service;

import com.sep.message.vo.EnumVo;

import java.util.Collection;

/**
 * <p>
 * 枚举 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-19
 */
public interface EnumService {

    /**
     * 通过枚举id查询
     *
     * @param type 枚举id
     * @return 枚举
     */
    EnumVo find(String type);

    /**
     * 查询所有枚举
     *
     * @return 所有枚举
     */
    Collection<EnumVo> find();

}