package com.sep.media.service;

import com.sep.content.dto.IdDto;
import com.sep.content.dto.UpdateStatusDto;
import com.sep.media.dto.AddMediaClassifyDto;
import com.sep.media.dto.UpdateMediaClassifySortDto;
import com.sep.media.model.MediaClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.media.vo.MediaClassifyVo;

import java.util.List;

/**
 * <p>
 * 文章分类表  服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-22
 */
public interface MediaClassifyService extends IService<MediaClassify> {
    /**
     * 管理后台接口
     * 添加资讯分类
     */
    Integer addMediaClassify(AddMediaClassifyDto addMediaClassifyDto);

    /**
     * 管理后台接口
     * 更新资讯状态
     */
    Integer updateMediaClassifyStatus(UpdateStatusDto updateStatusDto);

    /**
     * 管理后台接口
     * 更新资讯排序
     */
    Integer UpdateMediaClassifySort(UpdateMediaClassifySortDto updateMediaClassifySortDto);

    /**
     * 管理后台接口
     * 查询全部资讯  分类
     */
    List<MediaClassifyVo> getMediaClassifysBack();

    /**
     * 小程序接口
     * 查询资讯分类
     */
    List<MediaClassifyVo> getMediaClassifysXcx(Integer top);

    /**
     * 红房子首页分类

     * */
    List<MediaClassifyVo> getMediaClassifysXcxForRedhouse();

    /**
     * 管理后台接口
     * 获取一条资讯
     */
    MediaClassifyVo getMediaClassify(IdDto idDto);

    /**
     * 内部接口
     * 减文章数量
     */
    Integer subtractMediaNum(IdDto idDto);

    /**
     * 内部接口
     * 加文章数量
     */
    Integer plusMediaNum(IdDto idDto);

    /**
     * 管理后台接口
     * 删除分类
     */
    Integer delMediaClassify(IdDto idDto);

    /**
     * 内部接口
     */
    List<MediaClassify> getMediaClassifys(List<Integer> ids);

}
