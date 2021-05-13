package com.sep.sku.service.impl;

import com.sep.sku.dto.IdDto;
import com.sep.sku.model.OrderCode;
import com.sep.sku.repository.OrderCodeMapper;
import com.sep.sku.service.OrderCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-21
 */
@Service
public class OrderCodeServiceImpl extends ServiceImpl<OrderCodeMapper, OrderCode> implements OrderCodeService {

    @Override
    public String get(IdDto searchCardDto) {
        OrderCode one = lambdaQuery().eq(OrderCode::getSkuId, searchCardDto.getId()).one();
        if (Objects.isNull(one)) {
            return null;
        }
        if (StringUtils.isEmpty(one.getCode())) {
            return null;
        }
        return one.getCode();
    }

    @Override
    public void add(IdDto searchCardDto, String admissionCode) {
        OrderCode s = lambdaQuery().eq(OrderCode::getSkuId,searchCardDto.getId()).one();
        if (!Objects.isNull(s)) {
            if (StringUtils.isEmpty(s.getCode())) {
                s.setCode(admissionCode);
                s.updateById();
                return;
            }
        }
        OrderCode orderCode = new OrderCode();
        orderCode.setCode(admissionCode);
        orderCode.setSkuId(searchCardDto.getId());
        orderCode.insert();
    }
}
