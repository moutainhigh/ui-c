package com.sep.media.controller.Xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.IdDto;
import com.sep.media.dto.SearchMediaDto;
import com.sep.media.service.MediaService;
import com.sep.media.vo.MediaVo;
import com.sep.media.vo.MediaXcxVo;
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
 * 合作方表  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@RestController
@Api(value = "小程序合伙人相关api",tags = "小程序合伙人相关api")
@RequestMapping("/xcx/media")
public class XcxMediaController {
    @Autowired
    private MediaService mediaService;
    @PostMapping(value = "/searchMedia")
    @ApiOperation(value = "分页查询合伙人", httpMethod = "POST")
    public ResponseData<List<MediaXcxVo>> searchMedia() {
        return ResponseData.OK(mediaService.searchXcx());
    }

    @PostMapping(value = "/getMedia")
    @ApiOperation(value = "查询合伙人详情", httpMethod = "POST")
    public ResponseData<MediaVo> getMedia(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(mediaService.getMedia(idDto, true));
    }

    @PostMapping(value = "/getHomeMediaTop3")
    @ApiOperation(value = "首页查询合伙人", httpMethod = "POST")
    public ResponseData<List<MediaVo>> getHomeMediaTop3(@RequestBody SearchMediaDto searchMediaDto) {
        return ResponseData.OK(mediaService.getHomeMediaTop3(searchMediaDto));
    }

}

