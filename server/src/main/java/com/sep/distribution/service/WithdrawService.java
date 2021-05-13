package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.BaseUpdateDto;
import com.sep.distribution.dto.SearchWithdrawDto;
import com.sep.distribution.dto.WithdrawApplyDto;
import com.sep.distribution.dto.WithdrawPageSearchDto;
import com.sep.distribution.model.Withdraw;
import com.sep.distribution.vo.background.WithdrawPageSearchVo;
import com.sep.distribution.vo.xcx.WithdrawDetailsVo;

import java.math.BigDecimal;

/**
 * <p>
 * 提现表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface WithdrawService extends IService<Withdraw> {

    /**
     * 提交提现申请
     *
     * @param dto 请求参数
     * @return 申请是否提交成功
     */
    Boolean apply(WithdrawApplyDto dto);

    /**
     * 提现申请查询
     *
     * @param dto 请求参数
     * @return 申请详情
     */
    IPage<WithdrawDetailsVo> pageSearch(SearchWithdrawDto dto);

    /**
     * 申请详情查询
     *
     * @param id 申请id
     * @return 申请详情
     */
    WithdrawDetailsVo withdrawDetails(Integer id);

    /**
     * 可提现金额查询
     *
     * @param userId 用户id
     * @return 可提现金额
     */
    BigDecimal available(Integer userId);

    /**
     * 提现申请分页查询
     * @param dto 请求参数
     * @return 申请详情
     */
    IPage<WithdrawPageSearchVo> pageSearch(WithdrawPageSearchDto dto);

    /**
     * 通过
     * @param dto 请求参数
     * @return 是否成功
     */
    Boolean approve(BaseUpdateDto dto);

    /**
     * 通过
     * @param dto 请求参数
     * @return 是否成功
     */
    Boolean reject(BaseUpdateDto dto);

    BigDecimal totalWithdraw(Integer userId);



}