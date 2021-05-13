package com.sep.media.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.media.dto.AddMediaDto;
import com.sep.media.dto.SearchMediaDto;
import com.sep.media.model.Media;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.media.vo.MediaVo;
import com.sep.media.vo.MediaXcxVo;

import java.util.List;

/**
 * <p>
 * 合作方表  服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
public interface MediaService extends IService<Media> {
    Integer addAddMedia(AddMediaDto addMediaDto);

    /**
     * 通用接口 查询资讯
     * 查询全部资讯
     */
    IPage<MediaVo> searchMedia(SearchMediaDto searchMediaDto);
    List<MediaXcxVo> searchXcx();

    /**
     *  小程序接口
     * 查询首页资讯
     */
    List<MediaVo> getHomeMediaTop3(SearchMediaDto searchMediaDto);


    MediaVo getMedia(IdDto idDto, Boolean isReturnSkus);

    Integer delMedia(IdDto idDto);

    Integer updateMediaUpDownStatus(UpdateStatusDto updateStatusDto);

    /**
     * 内部接口
     *
     * 调用一次 加评论
     */
    Integer  plusComment(IdDto idDto);



    /**
     * 内部接口
     *
     * 调用一次 加评论
     */
    Integer  subtracComment(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer plusPraise(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer plusRetransmissionNum(IdDto idDto);


    /**
     * 内部接口
     *
     * 调用一次 点赞数量加一
     */
    Integer subtracPraise(IdDto idDto);

    /**
     * 内部接口
     *
     * ids查询
     */
    List<MediaVo> getMediaVos(List<Integer> ids);

    Boolean isMediaOneline(Integer id);
}
