package com.sep.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sep.common.utils.JwtUtils;
import com.sep.user.dto.AddOrUpdateAddressDto;
import com.sep.user.dto.WxUserAddressPageSearchDto;
import com.sep.user.model.Address;
import com.sep.user.model.Area;
import com.sep.user.repository.AddressMapper;
import com.sep.user.service.AddressService;
import com.sep.user.service.AreaService;
import com.sep.user.vo.AddressVo;
import com.sep.user.vo.WxUserAddressVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {


    @Autowired
    private AreaService areaService;

    @Resource
    private AddressMapper addressMapper;

//    @Override
//    public Integer addOrUpdateAddress(AddOrUpdateAddressDto addOrUpdateAddressDto) {
//        String userId = JwtUtils.parseJWT(addOrUpdateAddressDto.getToken()).get("id").toString();
//        if (StringUtils.isNotBlank(userId)) {
//            Address address = new Address();
//            address.setUserId(Integer.parseInt(userId));
//            BeanUtils.copyProperties(addOrUpdateAddressDto, address);
//            if (address.getId() != null && address.getId() > 0) {
//                return address.updateById() ? 1 : 0;
//            } else {
//                return address.insert() ? 1 : 0;
//            }
//        }
//        return 0;
//    }
//
//    @Override
//    public Integer delAddress(Integer id) {
//        return removeById(id) ? 1 : 0;
//    }
//
//    @Override
//    public List<AddressVo> getUserAddress(String token) {
//        String userId = JwtUtils.parseJWT(token).get("id").toString();
//        if (StringUtils.isNotBlank(userId)) {
//            return lambdaQuery().eq(Address::getUserId, Integer.parseInt(userId)).list().stream().map(e -> {
//                AddressVo vo = new AddressVo();
//                vo.setAreaCodeStr(areaService.getArea(new Long(e.getAreaCode())).getFullname());
//                BeanUtils.copyProperties(e, vo);
//                return vo;
//            }).collect(Collectors.toList());
//        }
//        return null;
//    }
//
//    @Override
//    public AddressVo getAcquiesce(String token) {
//        String userId = JwtUtils.parseJWT(token).get("id").toString();
//        if (StringUtils.isNotBlank(userId)) {
//            List<Address> list = lambdaQuery().eq(Address::getUserId, Integer.parseInt(userId)).orderByDesc(Address::getCreateTime).last("limit 0,1").list();
//            if (!list.isEmpty()) {
//                AddressVo result = new AddressVo();
//                result.setAreaCodeStr(areaService.getArea(new Long(list.get(0).getAreaCode())).getFullname());
//                BeanUtils.copyProperties(list.get(0), result);
//                return result;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public AddressVo getDetails(Integer id) {
//        Address addres = getById(id);
//        if (addres != null) {
//            AddressVo result = new AddressVo();
//            result.setAreaCodeStr(areaService.getArea(new Long(addres.getAreaCode())).getFullname());
//            BeanUtils.copyProperties(addres, result);
//            return result;
//        }
//        return null;
//    }
//
//    @Override
//    public IPage<WxUserAddressVo> pageSearch(WxUserAddressPageSearchDto dto) {
//        IPage<WxUserAddressVo> result = new Page<>();
//        LambdaQueryWrapper<Address> lambda = new QueryWrapper<Address>().lambda();
//        lambda.orderByDesc(Address::getCreateTime);
//        if (Objects.nonNull(dto.getUserId())) {
//            lambda.eq(Address::getUserId, dto.getUserId());
//        }
//        IPage<Address> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
//        BeanUtils.copyProperties(page, result);
//        if (CollectionUtils.isEmpty(page.getRecords())) {
//            return result;
//        }
//        result.setRecords(addressVoConvertor(page.getRecords()));
//        return result;
//    }

    private List<WxUserAddressVo> addressVoConvertor(List<Address> records) {
        Set<Integer> maxIds = maxIds(records);

        List<Long> areaIds = records.stream().map(address -> Long.valueOf(address.getAreaCode()))
                .collect(Collectors.toList());
        Collection<Area> areas = areaService.listByIds(areaIds);
        Map<Integer, Area> areaMap = areas.stream().collect(Collectors.toMap(Area::getOrders, area -> area));

        return records.stream().map(address -> {
            WxUserAddressVo vo = new WxUserAddressVo();
            BeanUtils.copyProperties(address, vo);
            if (areaMap.containsKey(Integer.valueOf(address.getAreaCode()))) {
                Area area = areaMap.get(Integer.valueOf(address.getAreaCode()));
                vo.setAreaCodeStr(area.getFullname());
            }
            vo.setAcquiesce(maxIds.contains(address.getId()));
            return vo;
        }).collect(Collectors.toList());
    }

    private Set<Integer> maxIds(List<Address> records) {
        Set<Integer> userIds = records.stream().map(Address::getUserId)
                .collect(Collectors.toSet());
        LambdaQueryWrapper<Address> lambda = new QueryWrapper<Address>().lambda();
        lambda.in(Address::getUserId, userIds);
        lambda.groupBy(Address::getUserId);
        return addressMapper.maxIds(lambda);
    }

}