package com.sep.content.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.SearchBannerDto;
import com.sep.content.service.BannerService;
import com.sep.content.vo.BannerVo;
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
 * 站点配图表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/xcx/banner")
@Api(value = "小程序banner API", tags = {"小程序banner API"})
public class XcxBannerController {

    @Autowired
    private BannerService bannerService;


    @PostMapping(value = "/getBannersByType")
    @ApiOperation(value = "获取banner", httpMethod = "POST")
    public ResponseData<List<BannerVo>> getBannersByType(@RequestBody SearchBannerDto searchBannerDto) {
        return ResponseData.OK(bannerService.getBannersByType(searchBannerDto));
    }


}
