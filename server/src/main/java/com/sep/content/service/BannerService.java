package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddBannerDto;
import com.sep.content.dto.IdDto;
import com.sep.content.dto.SearchBannerDto;
import com.sep.content.model.Banner;
import com.sep.content.vo.BannerVo;

import java.util.List;

/**
 * <p>
 * 站点配图表 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface BannerService extends IService<Banner> {


    /**
     * 管理后台接口
     * 添加或修改banner
     */
    Integer addOrUpdateBanner(AddBannerDto addBannerDto);

    /**
     * 管理后台接口
     * 查看详情
     */
    BannerVo getBanner(IdDto idDto);

    /**
     * 管理后台接口
     * 删除banner
     */
    Integer delBanner(IdDto idDto);

    /**
     * 管理后台接口
     * 查询banner列表
     */
    IPage<BannerVo> searchBanner(SearchBannerDto searchBannerDto);

    /**
     * 微信小程序接口
     * 根据类型 查询banner
     */
    List<BannerVo> getBannersByType(SearchBannerDto searchBannerDto);


}
