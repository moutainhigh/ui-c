package com.sep.user.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.user.dto.WxUserAddressPageSearchDto;
import com.sep.user.service.AddressService;
import com.sep.user.vo.WxUserAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后端微信用户收货地址相关API
 * </p>
 *
 * @author tianyu
 * @since 2020-01-16
 */
@RestController
@RequestMapping("/background/address")
@Api(value = "后端微信用户收货地址相关API", tags = {"后端微信用户收货地址相关API"})
public class BackgroundAddressController extends BaseController {

    @Autowired
    private AddressService addressService;

//    @ApiOperation(value = "分页查询", httpMethod = "POST")
//    @PostMapping(value = "/page")
//    public ResponseData<IPage<WxUserAddressVo>> pageSearch(@RequestBody WxUserAddressPageSearchDto dto) {
//        return ResponseData.OK(addressService.pageSearch(dto));
//    }

}