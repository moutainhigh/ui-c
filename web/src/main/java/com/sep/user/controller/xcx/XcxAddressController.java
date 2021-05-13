package com.sep.user.controller.xcx;



import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.user.dto.AddOrUpdateAddressDto;
import com.sep.user.dto.IdDto;
import com.sep.user.dto.TokenDto;
import com.sep.user.service.AddressService;
import com.sep.user.vo.AddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
@RestController
@RequestMapping("/xcx/address")
@Api(value = "小程序用户收货地址相关API", tags = {"小程序用户收货地址相关API"})
public class XcxAddressController extends BaseController {

    @Autowired
    private AddressService addressService;


//    @PostMapping(value = "/addOrUpdateAddress")
//    @ApiOperation(value = "添加或修改用户收货地址", httpMethod = "POST")
//    public ResponseData<Integer> addOrUpdateAddress(@RequestBody @Valid AddOrUpdateAddressDto addOrUpdateAddressDto) {
//        return ResponseData.OK(addressService.addOrUpdateAddress(addOrUpdateAddressDto));
//    }
//
//    @PostMapping(value = "/delAddress")
//    @ApiOperation(value = "删除用户收货地址", httpMethod = "POST")
//    public ResponseData<Integer> delAddress(@RequestBody IdDto idDto) {
//        return ResponseData.OK(addressService.delAddress(idDto.getId()));
//    }
//
//
//    @PostMapping(value = "/getUserAddress")
//    @ApiOperation(value = "获取用户收货地址集合", httpMethod = "POST")
//    public ResponseData<List<AddressVo>> getUserAddress(@RequestBody TokenDto token) {
//        return ResponseData.OK(addressService.getUserAddress(token.getToken()));
//    }
//
//
//    @PostMapping(value = "/getAcquiesce")
//    @ApiOperation(value = "获取默认地址", httpMethod = "POST")
//    public ResponseData<AddressVo> getAcquiesce(@RequestBody TokenDto token) {
//        return ResponseData.OK(addressService.getAcquiesce(token.getToken()));
//    }
//
//
//    @PostMapping(value = "/getDetails")
//    @ApiOperation(value = "获取地址详情", httpMethod = "POST")
//    public ResponseData<AddressVo> getDetails(@RequestBody IdDto idDto) {
//        return ResponseData.OK(addressService.getDetails(idDto.getId()));
//    }
}

