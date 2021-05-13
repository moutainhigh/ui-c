package com.sep.sku.repository;

import com.sep.sku.model.OrderInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 关联手机号码  Mapper 接口
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
public interface OrderInformationMapper extends BaseMapper<OrderInformation> {

    void addAll(@Param("orderInformations") List<OrderInformation> orderInformations);
}
