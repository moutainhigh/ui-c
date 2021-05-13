package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.*;
import com.sep.distribution.model.Identity;
import com.sep.distribution.vo.background.IdentityPageSearchVo;
import com.sep.distribution.vo.background.IdentitySelectVo;
import com.sep.distribution.vo.xcx.IdentityDetailsVo;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 分销身份表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface IdentityService extends IService<Identity> {

    /**
     * 查询所有已经启用的
     *
     * @return 消费身份集合
     */
    List<IdentityDetailsVo> searchEnabled();

    /**
     * 获取在用的分销身份
     *
     * @param id 身份id
     * @return 分销身份
     */
    Optional<Identity> getEnabledById(Integer id);

    /**
     * 添加分销身份
     *
     * @param dto 请求参数
     * @return 添加是否成功
     */
    boolean add(IdentityAddDto dto);

    /**
     * 修改分销身份
     *
     * @param dto 请求参数
     * @return 修改是否成功
     */
    boolean update(IdentityUpdateDto dto);

    /**
     * 启用
     *
     * @param dto 请求参数
     * @return 启用是否成功
     */
    boolean enable(BaseUpdateDto dto);

    /**
     * 停用
     *
     * @param dto 请求参数
     * @return 停用是否成功
     */
    boolean disable(BaseUpdateDto dto);

    /**
     * 分页查询
     *
     * @param dto 查询请求参数
     * @return 分销身份集合
     */
    IPage<IdentityPageSearchVo> pageSearch(IdentityPageSearchDto dto);

    /**
     * 查询所有
     *
     * @return 分销身份下拉对象
     */
    List<IdentitySelectVo> selectAll();


    IdentityDto get();





}