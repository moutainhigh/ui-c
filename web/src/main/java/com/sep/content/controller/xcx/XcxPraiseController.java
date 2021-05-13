package com.sep.content.controller.xcx;



import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddPraiseDto;
import com.sep.content.dto.AddRetransmissionDto;
import com.sep.content.service.PraiseService;
import com.sep.content.service.RetransmissionLogService;
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
@RequestMapping("/xcx/praise")
@Api(value = "小程序点赞相关API", tags = {"小程序点赞相关API"})
public class XcxPraiseController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private RetransmissionLogService retransmissionLogService;


    @PostMapping(value = "/addPraise")
    @ApiOperation(value = "点赞", httpMethod = "POST")
    public ResponseData<Integer> addPraise(@RequestBody @Valid AddPraiseDto addPraiseDto) {
        return ResponseData.OK(praiseService.addPraise(addPraiseDto));
    }

    @PostMapping(value = "/delPraise")
    @ApiOperation(value = "取消点赞", httpMethod = "POST")
    public ResponseData<Integer> delPraise(@RequestBody @Valid AddPraiseDto addPraiseDto) {
        return ResponseData.OK(praiseService.delPraise(addPraiseDto));
    }

    @PostMapping(value = "/isPraise")
    @ApiOperation(value = "是否点赞", httpMethod = "POST")
    public ResponseData<Integer> isPraise(@RequestBody @Valid AddPraiseDto addPraiseDto) {
        return ResponseData.OK(praiseService.isPraise(addPraiseDto));
    }
    @PostMapping(value = "/addRetransmission")
    @ApiOperation(value = "添加转发日志", httpMethod = "POST")
    public ResponseData<Integer> addRetransmission(@RequestBody @Valid AddRetransmissionDto addRetransmissionDto) {
        return ResponseData.OK(retransmissionLogService.addRetransmission(addRetransmissionDto));
    }


}
