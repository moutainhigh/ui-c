package com.sep.point.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.dto.PointPageSearchDto;
import com.sep.point.dto.ProductsExchangeInput;
import com.sep.point.model.Point;
import com.sep.point.vo.PointVo;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
public interface PointService extends IService<Point> {

    /**
     * 增加积分
     *
     * @param input         请求参数
     * @return 是否成功
     */
    boolean increase(PointIncreaseInput input);

    /**
     * 兑换商品
     *
     * @param input 请求参数
     * @return 是否成功
     */
    boolean productsExchange(ProductsExchangeInput input);

    /**
     * 剩余积分
     *
     * @param userId 用户ID
     * @return 剩余积分
     */
    int current(Integer userId);

    /**
     * 用户积分查询
     *
     * @param dto 请求参数
     * @return 积分详情
     */
    IPage<PointVo> pageSearch(PointPageSearchDto dto);

}