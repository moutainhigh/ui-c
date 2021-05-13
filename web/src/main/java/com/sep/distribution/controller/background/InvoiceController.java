package com.sep.distribution.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.distribution.dto.InvoiceUsersDto;
import com.sep.distribution.dto.InvoicesDto;
import com.sep.distribution.service.InvoiceService;
import com.sep.distribution.service.InvoiceUserService;
import com.sep.distribution.vo.background.InvoiceUserVo;
import com.sep.distribution.vo.background.InvoiceVo;
import com.sep.sku.dto.IdDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 发票填写项表  前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/distribution/background/invoice")
@Api(value = "后端发票api", tags = {"后端发票api"})
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceUserService invoiceUserService;
    @ApiOperation("添加需提交项")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody InvoicesDto invoiceDto) {
        return ResponseData.OK(invoiceService.add(invoiceDto));
    }

    @ApiOperation("删除提交项")
    @PostMapping("/del")
    public ResponseData<Integer> del(@RequestBody IdDto idDto) {
        return ResponseData.OK(invoiceService.del(idDto.getId()));
    }

    @ApiOperation("查询提交项")
    @PostMapping("/get")
    public ResponseData<List<InvoiceVo>> get() {
        return ResponseData.OK(invoiceService.get());
    }
    @ApiOperation("查询提交项")
    @PostMapping("/getNotNull")
    public ResponseData<List<InvoiceVo>> getNotNull() {
        return ResponseData.OK(invoiceUserService.get());
    }
    @ApiOperation("查询用户提交发票")
    @PostMapping("/getUser")
    public ResponseData<List<InvoiceUserVo>> getUser(@RequestBody InvoiceUsersDto invoiceUsersDto) {
        return ResponseData.OK(invoiceUserService.getUser(invoiceUsersDto));
    }
}
