package com.sep.user.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.user.dto.IdDto;
import com.sep.user.dto.WxUserPageSearchDto;
import com.sep.user.model.Facility;
import com.sep.user.service.FacilityService;
import com.sep.user.service.WxUserService;
import com.sep.user.vo.WxUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 后端微信用户相关API
 * </p>
 *
 * @author tianyu
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/background/wx/user")
@Api(value = "后端微信用户相关API", tags = {"后端微信用户相关API"})
public class BackgroundWxUserController extends BaseController {

    @Resource
    private WxUserService wxUserService;

    @Autowired
    private FacilityService facilityService;

    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "/page")
    public ResponseData<IPage<WxUserVo>> pageSearch(@RequestBody WxUserPageSearchDto dto) {
        return ResponseData.OK(wxUserService.pageSearch(dto));
    }

    @ApiOperation(value = "查询用户位置", httpMethod = "POST")
    @PostMapping(value = "/userLocalList")
    public ResponseData<List<Facility>> userLocalList(@RequestBody IdDto idDto) {
        return ResponseData.OK(facilityService.list(idDto.getId()));
    }

    @ApiOperation(value = "用户数统计", httpMethod = "GET")
    @GetMapping(value = "/count")
    public ResponseData<Long> count() {
        return ResponseData.OK(wxUserService.count());
    }

}