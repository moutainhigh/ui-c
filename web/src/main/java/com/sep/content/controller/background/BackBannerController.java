package com.sep.content.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddBannerDto;
import com.sep.content.dto.IdDto;
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

/**
 * <p>
 * 站点配图表 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/banner")
@Api(value = "管理后台banner相关API", tags = {"管理后台banner相关API"})
public class BackBannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping(value = "/addOrUpdateBanner")
    @ApiOperation(value = "添加或更新banner", httpMethod = "POST")
    public ResponseData<Integer> addOrUpdateBanner(@RequestBody @Valid AddBannerDto addBannerDto) {
        return ResponseData.OK(bannerService.addOrUpdateBanner(addBannerDto));
    }

    @PostMapping(value = "/getBanner")
    @ApiOperation(value = "获取banner一条数据", httpMethod = "POST")
    public ResponseData<BannerVo> getBanner(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(bannerService.getBanner(idDto));
    }


    @PostMapping(value = "/delBanner")
    @ApiOperation(value = "删除一条banner", httpMethod = "POST")
    public ResponseData<BannerVo> delBanner(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(bannerService.delBanner(idDto));
    }


    @PostMapping(value = "/searchBanner")
    @ApiOperation(value = "查询banner列表", httpMethod = "POST")
    public ResponseData<IPage<BannerVo>> searchBanner(@RequestBody SearchBannerDto searchBannerDto) {
        return ResponseData.OK(bannerService.searchBanner(searchBannerDto));
    }


}
