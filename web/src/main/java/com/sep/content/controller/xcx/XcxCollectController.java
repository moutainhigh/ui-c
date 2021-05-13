package com.sep.content.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.CollectDto;
import com.sep.content.dto.SearchColleDto;
import com.sep.content.service.CollectService;
import com.sep.content.vo.CollectVo;
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
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/xcx/collect")
@Api(value = "小程序收藏 API", tags = {"小程序收藏 API"})
public class XcxCollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping(value = "/addCollec")
    @ApiOperation(value = "添加收藏", httpMethod = "POST")
    public ResponseData<Integer> addCollec(@RequestBody @Valid CollectDto collectDto) {
        return ResponseData.OK(collectService.addCollec(collectDto));
    }

    @PostMapping(value = "/deCollec")
    @ApiOperation(value = "取消收藏", httpMethod = "POST")
    public ResponseData<Integer> deCollec(@RequestBody @Valid CollectDto collectDto) {
        return ResponseData.OK(collectService.deCollec(collectDto));
    }

    @PostMapping(value = "/isCollec")
    @ApiOperation(value = "是否收藏", httpMethod = "POST")
    public ResponseData<Integer> isCollec(@RequestBody @Valid CollectDto collectDto) {
        return ResponseData.OK(collectService.isCollec(collectDto));
    }

    @PostMapping(value = "/searchColle")
    @ApiOperation(value = "收藏列表", httpMethod = "POST")
    public ResponseData<IPage<CollectVo>> searchColle(@RequestBody @Valid SearchColleDto searchColleDto) {
        return ResponseData.OK(collectService.searchColle(searchColleDto));
    }

    @PostMapping(value = "/getCollecNum")
    @ApiOperation(value = "获取收藏数量", httpMethod = "POST")
    public ResponseData<Integer> getCollecNum(@RequestBody @Valid CollectDto collectDto) {
        return ResponseData.OK(collectService.getCollecNum(collectDto.getObjType(), collectDto.getObjId()));
    }

}
