package com.sep.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddFriendLinkDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchFriendLinkDto;
import com.sep.content.model.FriendLink;
import com.sep.content.vo.FriendLinkVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-23
 */
public interface FriendLinkService extends IService<FriendLink> {


    List<FriendLinkVo> getFriendLinkVos(SearchFriendLinkDto searchFriendLinkDto);


    Integer  addFriendLink(AddFriendLinkDto addFriendLinkDto);

    Integer delFriendLink(IdDto idDto);
}
