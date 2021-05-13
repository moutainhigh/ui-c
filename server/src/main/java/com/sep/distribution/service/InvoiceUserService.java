package com.sep.distribution.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.InvoiceUserDto;
import com.sep.distribution.dto.InvoiceUsersDto;
import com.sep.distribution.model.InvoiceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.distribution.vo.background.InvoiceUserVo;
import com.sep.distribution.vo.background.InvoiceVo;

import java.util.List;

/**
 * <p>
 * 用户申请表  服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-15
 */
public interface InvoiceUserService extends IService<InvoiceUser> {

    Integer add(InvoiceUserDto invoiceUser);

    List<InvoiceVo> get();

    IPage<InvoiceUserVo> getUser(InvoiceUsersDto invoiceUsersDto);

    InvoiceUserVo getXcxUser(String token,String orderNo);
}
