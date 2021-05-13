package com.sep.media.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.media.dto.AddMediaClassifyDto;
import com.sep.media.dto.UpdateMediaClassifySortDto;
import com.sep.media.service.MediaClassifyService;
import com.sep.media.vo.MediaClassifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
 * 文章分类表  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
@RestController
@Api(value = "合伙人分类相关api",tags = "合伙人分类相关api")
@RequestMapping("/background/media-classify")
public class MediaClassifyController {
    @Autowired
    private MediaClassifyService mediaClassifyService;


    @PostMapping(value = "/addmediaClassify")
    @ApiOperation(value = " 添加合伙人分类", httpMethod = "POST")
    public ResponseData<Integer> addmediaClassify(@RequestBody @Valid AddMediaClassifyDto addmediaClassifyDto) {
        return ResponseData.OK(mediaClassifyService.addMediaClassify(addmediaClassifyDto));
    }

    @PostMapping(value = "/getmediaClassifysBack")
    @ApiOperation(value = "合伙人分类列表", httpMethod = "POST")
    public ResponseData<List<MediaClassifyVo>> getmediaClassifysBack() {
        return ResponseData.OK(mediaClassifyService.getMediaClassifysBack());
    }

    @PostMapping(value = "/getmediaClassify")
    @ApiOperation(value = "获取一条合伙人分类", httpMethod = "POST")
    public ResponseData<MediaClassifyVo> getmediaClassify(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(mediaClassifyService.getMediaClassify(idDto));
    }

    @PostMapping(value = "/updatemediaClassifyStatus")
    @ApiOperation(value = " 更新合伙人状态", httpMethod = "POST")
    public ResponseData<Integer> updatemediaClassifyStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        return ResponseData.OK(mediaClassifyService.updateMediaClassifyStatus(updateStatusDto));
    }

    @PostMapping(value = "/UpdatemediaClassifySort")
    @ApiOperation(value = " 更新合伙人排序", httpMethod = "POST")
    public ResponseData<Integer> UpdatemediaClassifySort(@RequestBody @Valid UpdateMediaClassifySortDto updatemediaClassifySortDto) {
        return ResponseData.OK(mediaClassifyService.UpdateMediaClassifySort(updatemediaClassifySortDto));
    }

    @PostMapping(value = "/delmediaClassify")
    @ApiOperation(value = "删除合伙人", httpMethod = "POST")
    public ResponseData<Integer> delmediaClassify(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(mediaClassifyService.delMediaClassify(idDto));
    }
}
