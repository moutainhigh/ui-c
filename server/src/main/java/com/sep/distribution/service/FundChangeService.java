package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.dto.SearchFundChangeDto;
import com.sep.distribution.model.DistributionFundChange;
import com.sep.distribution.vo.xcx.FundChangeDetailsVo;

import java.math.BigDecimal;

/**
 * <p>
 * 资金变动表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
public interface FundChangeService extends IService<DistributionFundChange> {

    /**
     * 分页查询
     *
     * @param dto 请求参数
     * @return 资金变动列表
     */
    IPage<FundChangeDetailsVo> pageSearch(SearchFundChangeDto dto);

    /**
     * 添加记录
     *
     * @return 是否成功
     */
    /**
     * 添加记录
     *
     * @param type          变动类型
     * @param beforeAamount 变动前金额
     * @param amount        变动金额
     * @param userId        用户ID
     * @return 是否成功
     */
    boolean add(Integer type, BigDecimal beforeAamount, BigDecimal amount, Integer userId);

}