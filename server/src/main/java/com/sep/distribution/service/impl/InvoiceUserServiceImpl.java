package com.sep.distribution.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.distribution.dto.InvoiceUserDto;
import com.sep.distribution.dto.InvoiceUsersDto;
import com.sep.distribution.model.Invoice;
import com.sep.distribution.model.InvoiceUser;
import com.sep.distribution.repository.InvoiceUserMapper;
import com.sep.distribution.service.InvoiceService;
import com.sep.distribution.service.InvoiceUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.distribution.vo.background.InvoiceUserVo;
import com.sep.distribution.vo.background.InvoiceVo;
import com.sep.sku.model.Order;
import com.sep.sku.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户申请表  服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-15
 */
@Service
public class InvoiceUserServiceImpl extends ServiceImpl<InvoiceUserMapper, InvoiceUser> implements InvoiceUserService {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private OrderService orderService;

    @Override
    public Integer add(InvoiceUserDto invoiceUser) {
        int userId = (int) JwtUtils.parseJWT(invoiceUser.getToken()).get("id");
        Order one = orderService.lambdaQuery().eq(Order::getOrderNo, invoiceUser.getOrderId()).one();
        if (one == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单不存在");
        }
        InvoiceUser one1 = lambdaQuery().eq(InvoiceUser::getOrderId, one.getId()).eq(InvoiceUser::getUserId, userId).one();
        InvoiceUser invoiceUser1 = new InvoiceUser();
        BeanUtils.copyProperties(invoiceUser, invoiceUser1);
        if (one1 != null) {
            invoiceUser1.setId(one1.getId());
        }
        invoiceUser1.setUserId(userId);
        invoiceUser1.setOrderId(one.getId());
        if ((invoiceUser.getId() != null && invoiceUser.getId() != 0) || (invoiceUser1.getId() != null && invoiceUser1.getId() != 0)) {
            invoiceUser1.setUpdatedTime(LocalDateTime.now());
            return invoiceUser1.updateById() ? 1 : 0;
        } else {
            invoiceUser1.setCreatedTime(LocalDateTime.now());
            return invoiceUser1.insert() ? 1 : 0;
        }
    }

    @Override
    public List<InvoiceVo> get() {
        List<Invoice> list = invoiceService.lambdaQuery().list();
        return list.stream().map(e -> {
            InvoiceVo invoiceVo = new InvoiceVo();
            invoiceVo.setId(e.getId());
            invoiceVo.setIds("remark" + e.getId());
            invoiceVo.setName(e.getName());
            return invoiceVo;
        }).filter(e -> StringUtils.isNotEmpty(e.getName())).collect(Collectors.toList());
    }

    @Override
    public IPage<InvoiceUserVo> getUser(InvoiceUsersDto invoiceUsersDto) {
        LambdaQueryWrapper<InvoiceUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(InvoiceUser::getOrderId, invoiceUsersDto.getOrderID());
        lambdaQueryWrapper.orderByDesc(InvoiceUser::getCreatedTime);
        IPage<InvoiceUser> objectPage = new Page<>();
        objectPage.setSize(invoiceUsersDto.getPageSize());
        objectPage.setCurrent(invoiceUsersDto.getCurrentPage());
        IPage<InvoiceUser> invoiceUserIPage = baseMapper.selectPage(objectPage, lambdaQueryWrapper);
        IPage<InvoiceUserVo> invoiceUserVoIPage = new Page<>();
        invoiceUserVoIPage.setSize(invoiceUsersDto.getPageSize());
        invoiceUserVoIPage.setCurrent(invoiceUsersDto.getCurrentPage());
        List<InvoiceUser> records = invoiceUserIPage.getRecords();
        invoiceUserVoIPage.setTotal(invoiceUserIPage.getTotal());
        if (records != null) {
            invoiceUserVoIPage.setRecords(records.stream().map(e -> {
                InvoiceUserVo invoiceUserVo = new InvoiceUserVo();
                BeanUtils.copyProperties(e, invoiceUserVo);
                return invoiceUserVo;
            }).collect(Collectors.toList()));
        }
        return invoiceUserVoIPage;
    }

    @Override
    public InvoiceUserVo getXcxUser(String token, String orderNo) {
        String userId = JwtUtils.parseJWT(token).get("id").toString();
        Order one = orderService.lambdaQuery().eq(Order::getOrderNo, orderNo).one();
        if (one == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单不存在");
        }
        InvoiceUser one1 = lambdaQuery().eq(InvoiceUser::getUserId, userId).eq(InvoiceUser::getOrderId, one.getId()).one();
        InvoiceUserVo invoiceUserVo = new InvoiceUserVo();
        BeanUtils.copyProperties(one1, invoiceUserVo);
        return invoiceUserVo;
    }
}
