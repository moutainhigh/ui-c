package com.sep.distribution.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.*;
import com.sep.distribution.service.WithdrawService;
import com.sep.distribution.view.excel.WithdrawView;
import com.sep.distribution.vo.background.WithdrawPageSearchVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台提现相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/distribution/background/withdraw")
@Api(value = "后台提现相关API", tags = {"后台提现相关API"})
public class DistributionBackgroundWithdrawController {

    @Resource
    private WithdrawService withdrawService;

    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public ResponseData<IPage<WithdrawPageSearchVo>> pageSearch(@RequestBody WithdrawPageSearchDto dto) {
        return ResponseData.OK(withdrawService.pageSearch(dto));
    }

    @PutMapping(value = "/approve")
    @ApiOperation(value = "通过", httpMethod = "PUT")
    public ResponseData<Boolean> approve(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(withdrawService.approve(dto));
    }

    @PutMapping(value = "/reject")
    @ApiOperation(value = "驳回", httpMethod = "PUT")
    public ResponseData<Boolean> reject(@RequestBody BaseUpdateDto dto) {
        return ResponseData.OK(withdrawService.reject(dto));
    }

    @PostMapping(value = "/export")
    @ApiOperation(value = "提现导出", httpMethod = "POST")
    public ModelAndView export(@RequestBody WithdrawExportDto dto) {
        WithdrawPageSearchDto searchDto = new WithdrawPageSearchDto();
        BeanUtils.copyProperties(dto,searchDto);
        searchDto.setPageSize(Long.MAX_VALUE);
        IPage<WithdrawPageSearchVo> page = withdrawService.pageSearch(searchDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getRecords());
        return new ModelAndView(new WithdrawView(), map);
    }

}
