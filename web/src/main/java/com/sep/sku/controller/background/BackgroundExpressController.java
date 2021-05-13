package com.sep.sku.controller.background;


import com.google.common.collect.Lists;
import com.sep.sku.enums.ExpressCompanyEnum;
import com.sep.sku.vo.SearchExpressRespVo;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 快递相关 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/background/express")
@Api(value = "后台快递相关API", tags = {"后台快递相关API"})
public class BackgroundExpressController extends BaseController{

//    @PostMapping(value= "/getAllExpressCompany")
//    @ApiOperation(value = "查询所有快递公司信息",httpMethod = "POST")
//    public ResponseData<List<SearchExpressRespVo>> getAllExpressCompany(){
//        List<SearchExpressRespVo> searchExpressRespVoList = Lists.newArrayList();
//        Lists.newArrayList(ExpressCompanyEnum.values()).forEach(e->{
//            SearchExpressRespVo searchExpressRespVo = new SearchExpressRespVo();
//            searchExpressRespVo.setId(e.getCode());
//            searchExpressRespVo.setExpressName(e.getDescription());
//            searchExpressRespVoList.add(searchExpressRespVo);
//        });
//        return ResponseData.OK(searchExpressRespVoList);
//    }

}
