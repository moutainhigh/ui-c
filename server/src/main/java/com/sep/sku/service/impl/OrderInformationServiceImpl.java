package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.message.service.WxService;
import com.sep.message.vo.LetterRecordVo;
import com.sep.sku.dto.OrderInformationDto;
import com.sep.sku.model.Order;
import com.sep.sku.model.OrderInformation;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.repository.OrderInformationMapper;
import com.sep.sku.service.OrderInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.sku.service.OrderService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.OrderInformationPhoneVo;
import com.sep.sku.vo.OrderInformationVo;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.model.WxUser;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 关联手机号码  服务实现类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@Service
public class OrderInformationServiceImpl extends ServiceImpl<OrderInformationMapper, OrderInformation> implements OrderInformationService {
    @Autowired
    private OrderInformationMapper orderInformationMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private WxUserService wxUserService;

    @Override
    public void add(List<String> phones, String orderNo, Integer skuid) {
        String randomString = getRandomString(8);
        List<OrderInformation> orderInformations = phones.stream().map(e -> {
            OrderInformation orderInformation = new OrderInformation();
            orderInformation.setCreateTime(LocalDateTime.now());
            orderInformation.setOrderNo(orderNo);
            orderInformation.setSkuId(skuid);
            orderInformation.setPhone(e);
            orderInformation.setConsumableCode(randomString);
            return orderInformation;
        }).collect(Collectors.toList());
        orderInformationMapper.addAll(orderInformations);
    }
    private String getRandomString(int length) {
        //产生随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //循环length次
        for (int i = 0; i < length; i++) {
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result = Math.round(Math.random() * 25 + 65);
                    //将ASCII码转换成字符
                    sb.append((char) result);
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }

    @Override
    public List<OrderInformation> findSkuListByOrderNo(String orderNo) {
        return lambdaQuery().eq(OrderInformation::getOrderNo, orderNo).list();
    }

    @Override
    public OrderInformationVo getSkuOrderInfoById(Integer id) {
        Order one = orderService.lambdaQuery().eq(Order::getId, id).one();
        if (one == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单不存在");
        }
        List<OrderInformation> list = lambdaQuery().eq(OrderInformation::getOrderNo, one.getOrderNo()).list();
        OrderInformationVo orderInformationVo = new OrderInformationVo();
        orderInformationVo.setSize(0);
        BeanUtils.copyProperties(one, orderInformationVo);
        if (list != null && list.size() != 0) {
            SkuInfo one1 = skuInfoService.lambdaQuery().eq(SkuInfo::getId, list.get(0).getSkuId()).one();
            BeanUtils.copyProperties(one1, orderInformationVo);
            orderInformationVo.setPhones(list.stream().map(e->{
                OrderInformationPhoneVo orderInformationPhoneVo=new OrderInformationPhoneVo();
                orderInformationPhoneVo.setPhone(e.getPhone());
                orderInformationPhoneVo.setStatus(e.getState());
                return orderInformationPhoneVo;
            }).collect(Collectors.toList()));
            orderInformationVo.setSize(list.size());
            orderInformationVo.setOrderNo(list.get(0).getOrderNo());
            orderInformationVo.setUserId(one.getUserId());
        }
        orderInformationVo.setState(one.getStatus());
        return orderInformationVo;
    }

    @Override
    public Integer updateState(OrderInformationDto orderInformationDto) {
        List<OrderInformation> list = lambdaQuery()
                .eq(StringUtils.isNotEmpty(orderInformationDto.getPhone()),
                        OrderInformation::getPhone, orderInformationDto.getPhone())
                .eq(OrderInformation::getState, 0)
                .eq(OrderInformation::getSkuId, orderInformationDto.getId()).list();
        if (list == null || list.size() == 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您的票已全部用完");
        }
        OrderInformation orderInformation = list.get(0);
        orderInformation.setState(1);
        return orderInformation.updateById() ? 1 : 0;
    }

}
