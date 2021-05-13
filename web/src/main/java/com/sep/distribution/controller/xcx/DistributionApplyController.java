package com.sep.distribution.controller.xcx;


import com.sep.distribution.dto.BaseDto;
import com.sep.distribution.dto.DistributionApplyDto;
import com.sep.distribution.service.ApplyService;
import com.sep.distribution.vo.xcx.DistributionApplyDetailsVo;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 小程序分销申请相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/xcx/distribution/apply")
@Api(value = "小程序分销申请相关API", tags = {"小程序分销申请相关API"})
public class DistributionApplyController {

    @Resource
    private ApplyService applyService;

    @PostMapping(value = "/")
    @ApiOperation(value = "申请/重新申请", httpMethod = "POST")
    public ResponseData<Integer> apply(@RequestBody DistributionApplyDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(applyService.apply(dto));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "申请详情", httpMethod = "POST")
    public ResponseData<DistributionApplyDetailsVo> details(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(applyService.getByUserId(userId));
    }

}