package com.sep.content.controller.background;


import com.sep.common.model.response.ResponseData;
import com.sep.content.dto.AddFriendLinkDto;
import com.sep.content.dto.IdDto;
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
@RequestMapping("/background/friend-link")
@Api(value = "管理后台友情链接API", tags = {"管理后台友情链接API"})
public class BackFriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @PostMapping(value = "/getFriendLinkVos")
    @ApiOperation(value = "友情链接列表", httpMethod = "POST")
    public ResponseData<List<FriendLinkVo>> getFriendLinkVos(@RequestBody SearchFriendLinkDto searchFriendLinkDto) {
        return ResponseData.OK(friendLinkService.getFriendLinkVos(searchFriendLinkDto));
    }

    @PostMapping(value = "/addFriendLink")
    @ApiOperation(value = "添加或更新友情链接", httpMethod = "POST")
    public ResponseData<Integer> addFriendLink(@RequestBody @Valid AddFriendLinkDto addFriendLinkDto) {
        return ResponseData.OK(friendLinkService.addFriendLink(addFriendLinkDto));
    }

    @PostMapping(value = "/delFriendLink")
    @ApiOperation(value = "删除友情链接", httpMethod = "POST")
    public ResponseData<Integer> delFriendLink(@RequestBody @Valid IdDto idDto) {
        return ResponseData.OK(friendLinkService.delFriendLink(idDto));
    }

}
