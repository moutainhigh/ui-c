package com.sep.content.controller.xcx;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.SearchFriendLinkDto;
import com.sep.content.service.FriendLinkService;
import com.sep.content.vo.FriendLinkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/xcx/friend-link")
@Api(value = "小程序友情链接API", tags = {"小程序友情链接API"})
public class XcxFriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @PostMapping(value = "/getFriendLinkVos")
    @ApiOperation(value = "友情链接列表", httpMethod = "POST")
    public ResponseData<List<FriendLinkVo>> getFriendLinkVos(@RequestBody SearchFriendLinkDto searchFriendLinkDto) {
        return ResponseData.OK(friendLinkService.getFriendLinkVos(searchFriendLinkDto));
    }

}
