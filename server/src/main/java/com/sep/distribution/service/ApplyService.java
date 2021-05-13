package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.*;
import com.sep.distribution.model.Apply;
import com.sep.distribution.vo.background.*;
import com.sep.distribution.vo.xcx.DistributionApplyDetailsVo;

import java.util.Optional;

/**
 * <p>
 * 分销申请表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface ApplyService extends IService<Apply> {

    /**
     * 申请开通分销
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    Integer apply(DistributionApplyDto dto);

    /**
     * 查询提交的分销申请
     *
     * @param userId 用户ID
     * @return 申请详情
     */
    DistributionApplyDetailsVo getByUserId(Integer userId);

    /**
     * 查询已经通过分销申请
     *
     * @param userId 用户ID
     * @return 申请详情
     */
    Optional<Integer> getIdentityByUserId(Integer userId);

    /**
     * 分页查询
     *
     * @param dto 请求参数
     * @return 申请详情列表
     */
    IPage<ApplyPageSearchVo> pageSearch(ApplyPageSearchDto dto);

    /**
     * 通过
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean approve(BaseUpdateDto dto);

    /**
     * 驳回
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean reject(BaseUpdateDto dto);

    /**
     * 实名信息
     *
     * @param id 主键
     * @return 实名信息
     */
    ApplyRealNameVo realName(Integer id);

    /**
     * 分销用户信息分页查询
     *
     * @param dto 请求参数
     * @return 分销用户信息
     */
    IPage<DistributionUserVo> users(DistributionUserSearchDto dto);

    /**
     * 一级粉丝分页查询
     *
     * @param dto 请求参数
     * @return 粉丝信息
     */
    IPage<StairFansVo> stairFans(FansUserSearchDto dto);

    /**
     * 二级粉丝分页查询
     *
     * @param dto 请求参数
     * @return 粉丝信息
     */
    IPage<SecondLevelFansVo> secondLevelFans(FansUserSearchDto dto);

}