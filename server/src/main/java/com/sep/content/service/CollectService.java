package com.sep.content.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.CollectDto;
import com.sep.content.dto.SearchColleDto;
import com.sep.content.model.Collect;
import com.sep.content.vo.CollectVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-06
 */
public interface CollectService extends IService<Collect> {


    /**
     * 小程序端接口
     * 添加收藏
     */
    Integer addCollec(CollectDto collectDto);

    /**
     * 小程序端接口
     * 取消收藏
     * 取消后成功后根据类型对应更新点赞数量
     */
    Integer deCollec(CollectDto collectDto);

    /**
     * 小程序接口
     * 是否收藏
     */
    Integer isCollec(CollectDto collectDto);
    /**
     * 内部接口
     * 获取收藏数量
     */
    Integer getCollecNum(Integer objType, Integer objId);

    /**
     * 小程序接口
     * 用户收藏列表
     * */

    IPage<CollectVo> searchColle(SearchColleDto searchColleDto);


}
