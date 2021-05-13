package com.sep.distribution.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.distribution.dto.DistributionUserSearchDto;
import com.sep.distribution.dto.WithdrawExportDto;
import com.sep.distribution.dto.WithdrawPageSearchDto;
import com.sep.distribution.service.ApplyService;
import com.sep.distribution.service.WithdrawService;
import com.sep.distribution.view.excel.DistributionUserView;
import com.sep.distribution.view.excel.WithdrawView;
import com.sep.distribution.vo.background.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后端导出相关API
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Controller
@RequestMapping("/distribution/background/export")
@Api(value = "后端导出相关API", tags = {"后端导出相关API"})
public class DistributionBackgroundExportController {

    @Resource
    private ApplyService applyService;
    @Resource
    private WithdrawService withdrawService;

    @GetMapping(value = "/users")
    @ApiOperation(value = "分销用户导出", httpMethod = "GET")
    public ModelAndView usersExport(DistributionUserSearchDto dto) {
        IPage<DistributionUserVo> page = applyService.users(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getRecords());
        return new ModelAndView(new DistributionUserView(), map);
    }

    @GetMapping(value = "/withdraw")
    @ApiOperation(value = "提现导出", httpMethod = "GET")
    public ModelAndView export(WithdrawExportDto dto) {
        WithdrawPageSearchDto searchDto = new WithdrawPageSearchDto();
        BeanUtils.copyProperties(dto,searchDto);
        searchDto.setPageSize(Long.MAX_VALUE);
        IPage<WithdrawPageSearchVo> page = withdrawService.pageSearch(searchDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getRecords());
        return new ModelAndView(new WithdrawView(), map);
    }

}