package com.sep.distribution.service;

import com.sep.distribution.dto.InvoicesDto;
import com.sep.distribution.model.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.vo.background.InvoiceVo;

import java.util.List;

/**
 * <p>
 * 发票填写项表  服务类
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-15
 */
public interface InvoiceService extends IService<Invoice> {

    List<Integer> add(InvoicesDto invoiceDto);

    Integer del(Integer id);

    List<InvoiceVo> get();
}
