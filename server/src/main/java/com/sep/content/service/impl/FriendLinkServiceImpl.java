package com.sep.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.content.dto.AddFriendLinkDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchFriendLinkDto;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.FriendLink;
import com.sep.content.repository.FriendLinkMapper;
import com.sep.content.service.FriendLinkService;
import com.sep.content.vo.FriendLinkVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-23
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Override
    public List<FriendLinkVo> getFriendLinkVos(SearchFriendLinkDto searchFriendLinkDto) {
        List<FriendLink> list = lambdaQuery().like(StringUtils.isNotBlank(searchFriendLinkDto.getName()), FriendLink::getName, searchFriendLinkDto.getName())
                .orderByAsc(FriendLink::getSort).orderByDesc(FriendLink::getCreateTime).list();
        if (list != null && list.size() > 0) {
            return list.stream().map(e -> {
                FriendLinkVo vo = new FriendLinkVo();
                BeanUtils.copyProperties(e, vo);
                return vo;
            }).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public Integer addFriendLink(AddFriendLinkDto addFriendLinkDto) {
        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(addFriendLinkDto, friendLink);
        Integer sortNum=lambdaQuery().eq(FriendLink::getSort,addFriendLinkDto.getSort()).count();
        if(sortNum>0){
            throw new SepCustomException(BizErrorCode.SORT_ERROR);
        }
        if (friendLink.getId() != null && friendLink.getId() > 0) {
            return friendLink.updateById() ? 1 : 0;
        } else {
            return friendLink.insert() ? 1 : 0;
        }

    }

    @Override
    public Integer delFriendLink(IdDto idDto) {
        FriendLink friendLink = new FriendLink();
        friendLink.setId(idDto.getId());
        return friendLink.deleteById() ? 1 : 0;
    }
}
