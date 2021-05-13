package com.sep.media.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddArticleDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.media.dto.AddMediaDto;
import com.sep.media.dto.SearchMediaDto;
import com.sep.media.service.MediaService;
import com.sep.media.vo.MediaVo;
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
@Api(value = "合伙人相关api",tags = "合伙人相关api")
@RequestMapping("/background/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping(value = "/addAddArticle")
    @ApiOperation(value = "添加合伙人", httpMethod = "POST")
    public ResponseData<Integer> addAddArticle(@RequestBody @Valid AddMediaDto addArticleDto) {
        return ResponseData.OK(mediaService.addAddMedia(addArticleDto));
    }
    @PostMapping(value = "/searchMedia")
    @ApiOperation(value = "分页查询合伙人", httpMethod = "POST")
    public ResponseData<IPage<MediaVo>> searchMedia(@RequestBody SearchMediaDto searchMediaDto) {
        return ResponseData.OK(mediaService.searchMedia(searchMediaDto));
    }

    @PostMapping(value = "/getMedia")
    @ApiOperation(value = "查询合伙人详情", httpMethod = "POST")
    public ResponseData<MediaVo> getMedia(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(mediaService.getMedia(idDto, true));
    }
    @PostMapping(value = "/delArticle")
    @ApiOperation(value = "删除合伙人", httpMethod = "POST")
    public ResponseData<Integer> delArticle(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(mediaService.delMedia(idDto));
    }
    @PostMapping(value = "/updateArticleUpDownStatus")
    @ApiOperation(value = "更新合伙人状态", httpMethod = "POST")
    public ResponseData<Integer> updateArticleUpDownStatus(@RequestBody @Valid UpdateStatusDto updateStatusDto) {
        return ResponseData.OK(mediaService.updateMediaUpDownStatus(updateStatusDto));
    }
}

