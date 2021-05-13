package com.sep.sku.service;

import com.sep.sku.dto.IdDto;
import com.sep.sku.model.OrderCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *   服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-21
 */
public interface OrderCodeService extends IService<OrderCode> {

    String get(IdDto searchCardDto);

    void add(IdDto searchCardDto, String admissionCode);
}
