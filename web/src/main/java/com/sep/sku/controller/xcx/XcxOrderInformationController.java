package com.sep.sku.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.sku.dto.OrderInformationDto;
import com.sep.sku.service.OrderInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关联手机号码  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/xcx/order/information")
@Api(value = "小程序票号相关API", tags = {"小程序票号相关API"})
public class XcxOrderInformationController {
    @Autowired
    private OrderInformationService orderInformationService;

    //    @ApiOperation("添加测试")
//    @PostMapping("/order")
//    public void add(@RequestParam("list") List<String> list,@RequestParam("num") String num){
////        orderInformationService.add(list,num);
//    }
    @PostMapping(value = "/updateState")
    @ApiOperation(value = "二维码扫码", httpMethod = "POST")
    public ResponseData<Integer> updateState(@RequestBody OrderInformationDto orderInformationDto) {

        return ResponseData.OK(orderInformationService.updateState(orderInformationDto));
    }
}
