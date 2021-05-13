package com.sep.distribution.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.distribution.dto.InvoiceUserDto;
import com.sep.distribution.dto.InvoiceXcxDto;
import com.sep.distribution.service.InvoiceService;
import com.sep.distribution.service.InvoiceUserService;
import com.sep.distribution.vo.background.InvoiceVo;
import com.sep.sku.dto.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户申请表  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-15
 */
@RestController
@Api(value = "用户发票相关api", tags = {"用户发票相关api"})
@RequestMapping("/distribution/xcx/invoiceUser")
public class InvoiceUserController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceUserService invoiceUserService;

    @ApiOperation("查询提交项")
    @PostMapping("/get")
    public ResponseData<List<InvoiceVo>> get() {
        return ResponseData.OK(invoiceUserService.get());
    }
    @ApiOperation("查询提交项")
    @PostMapping("/getUser")
    public ResponseData<List<InvoiceVo>> getUser(@RequestBody InvoiceXcxDto tokenDto) {
        return ResponseData.OK(invoiceUserService.getXcxUser(tokenDto.getToken(),tokenDto.getOrderNo()));
    }
    @ApiOperation("用户添加提交项")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody InvoiceUserDto invoiceUser) {
        return ResponseData.OK(invoiceUserService.add(invoiceUser));
    }
}
