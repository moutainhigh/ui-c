package com.sep.sku.controller.background;


import com.sep.sku.model.OrderInformation;
import com.sep.sku.service.OrderInformationService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import net.sf.oval.guard.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 关联手机号码  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/order-information")
public class OrderInformationController {
    @Autowired
    private OrderInformationService orderInformationService;
//    @ApiOperation("添加测试")
//    @PostMapping("/order")
//    public void add(@RequestParam("list") List<String> list,@RequestParam("num") String num){
////        orderInformationService.add(list,num);
//    }

}
