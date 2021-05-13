package com.sep.distribution.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.SearchFundChangeDto;
import com.sep.distribution.service.FundChangeService;
import com.sep.distribution.vo.xcx.FundChangeDetailsVo;
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
 * 小程序资金变动相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/xcx/fundchange")
@Api(value = "小程序资金变动相关API", tags = {"小程序资金变动相关API"})
public class DistributionFundChangeController {

    @Resource
    private FundChangeService fundChangeService;

    @PostMapping(value = "/pageSearch")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<FundChangeDetailsVo>> pageSearch(@RequestBody SearchFundChangeDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(fundChangeService.pageSearch(dto));
    }

}