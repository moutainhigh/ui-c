package com.sep.point.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.point.dto.SearchFundChangeDto;
import com.sep.point.service.FundChangeService;
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
 * 小程序积分变动相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Api(value = "小程序积分变动相关API", tags = {"小程序积分变动相关API"})
@RestController
@RequestMapping("/point/xcx/fund-change")
public class PointXcxFundChangeController {

    @Resource
    private FundChangeService fundChangeService;

    @PostMapping(value = "/pageSearch")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<FundChangeVo>> pageSearch(@RequestBody SearchFundChangeDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        dto.setUserId(userId);
        return ResponseData.OK(fundChangeService.pageSearch(dto));
    }

}
