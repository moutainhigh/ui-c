package com.sep.media.controller.Xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.TopDto;
import com.sep.media.service.MediaClassifyService;
import com.sep.media.vo.MediaClassifyVo;
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
 * 文章分类表  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@RestController
@Api(value = "小程序合伙人分类相关api",tags = "小程序合伙人分类相关api")
@RequestMapping("/xcx/media-classify")
public class XcxMediaClassifyController {
    @Autowired
    private MediaClassifyService mediaClassifyService;


    @PostMapping(value = "/getMediaClassifysXcx")
    @ApiOperation(value = " 获取合伙人分类列表", httpMethod = "POST")
    public ResponseData<List<MediaClassifyVo>> getMediaClassifysXcx(@RequestBody TopDto topDto) {
        return ResponseData.OK(mediaClassifyService.getMediaClassifysXcx(topDto.getTop()));
    }

    @PostMapping(value = "/getMediaClassifysXcxForRedhouse")
    @ApiOperation(value = "获取红房子首页分类", httpMethod = "POST")
    public ResponseData<List<MediaClassifyVo>> getMediaClassifysXcxForRedhouse() {
        return ResponseData.OK(mediaClassifyService.getMediaClassifysXcxForRedhouse());
    }
}
