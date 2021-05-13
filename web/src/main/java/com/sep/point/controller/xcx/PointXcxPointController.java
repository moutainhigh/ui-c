package com.sep.point.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.point.dto.BaseDto;
import com.sep.point.dto.SearchEarningDto;
import com.sep.point.service.FundChangeService;
import com.sep.point.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 小程序端积分相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Api(value = "小程序端积分相关API", tags = {"小程序端积分相关API"})
@RestController
@RequestMapping("/point/xcx/point")
public class PointXcxPointController {

    @Resource
    private PointService pointService;
    @Resource
    private FundChangeService fundChangeService;

    @PostMapping(value = "/current")
    @ApiOperation(value = "剩余积分", httpMethod = "POST")
    public ResponseData<Integer> current(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(pointService.current(userId));
    }

    @PostMapping(value = "/today/earnings")
    @ApiOperation(value = "今日收益", httpMethod = "POST")
    public ResponseData<Integer> toDayEarnings(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(fundChangeService.toDayEarnings(userId));
    }

    @PostMapping(value = "/weekend/earnings")
    @ApiOperation(value = "本周收益", httpMethod = "POST")
    public ResponseData<Integer> weekendEarnings(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(fundChangeService.weekendEarnings(userId));
    }

    @PostMapping(value = "/current/month/earnings")
    @ApiOperation(value = "本月收益", httpMethod = "POST")
    public ResponseData<Integer> currentMonthEarnings(@RequestBody BaseDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(fundChangeService.currentMonthEarnings(userId));
    }

    @PostMapping(value = "/earnings")
    @ApiOperation(value = "收益查询", httpMethod = "POST")
    public ResponseData<Integer> SearchEarningDto(@RequestBody SearchEarningDto dto) {
        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
        return ResponseData.OK(fundChangeService.getPointByDateTimeRange(userId,
                LocalDateTime.of(dto.getStartTime(), LocalTime.MIN), LocalDateTime.of(dto.getEndTime(), LocalTime.MAX)));
    }

}