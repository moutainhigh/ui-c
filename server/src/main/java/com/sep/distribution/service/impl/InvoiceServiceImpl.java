package com.sep.distribution.service.impl;

import com.sep.distribution.dto.InvoiceDto;
import com.sep.distribution.dto.InvoicesDto;
import com.sep.distribution.model.Invoice;
import com.sep.distribution.repository.InvoiceMapper;
import com.sep.distribution.service.InvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.vo.background.InvoiceVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 发票填写项表  服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-15
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    @Override
    public List<Integer> add(InvoicesDto invoiceDto) {
        List<InvoiceDto> list = invoiceDto.getList();
        if (list != null && list.size() != 0) {
            return list.stream().map(e -> {
                Invoice invoice = new Invoice();
                invoice.setUpdatedTime(LocalDateTime.now());
                invoice.setName(e.getName());
                invoice.setId(e.getId());
                return invoice.updateById() ? 1 : 0;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Integer del(Integer id) {
        if (id == 0) {
            return 1;
        }
        return lambdaUpdate().eq(Invoice::getId, id).set(Invoice::getName, "").update() ? 1 : 0;
    }

    @Override
    public List<InvoiceVo> get() {
        List<Invoice> list = lambdaQuery().list();
        return list.stream().map(e -> {
            InvoiceVo invoiceVo = new InvoiceVo();
            invoiceVo.setId(e.getId());
            invoiceVo.setName(e.getName());
            return invoiceVo;
        }).collect(Collectors.toList());
    }
}
