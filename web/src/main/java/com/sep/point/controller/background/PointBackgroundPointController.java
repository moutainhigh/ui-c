package com.sep.point.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.point.dto.PointPageSearchDto;
import com.sep.point.service.PointService;
import com.sep.point.vo.FundChangeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后端积分相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Api(value = "后端积分相关API", tags = {"后端积分相关API"})
@RestController
@RequestMapping("/point/background/point")
public class PointBackgroundPointController {

    @Resource
    private PointService pointService;

    @PostMapping(value = "/pageSearch")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<FundChangeVo>> pageSearch(@RequestBody PointPageSearchDto dto) {
        return ResponseData.OK(pointService.pageSearch(dto));
    }

}