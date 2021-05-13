package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.IdDto;
import com.sep.sku.dto.FacilitatorDto;
import com.sep.sku.dto.SearchFacilitatorDto;
import com.sep.sku.model.Facilitator;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-05-19
 */
public interface FacilitatorService extends IService<Facilitator> {

    /**
     * 添加或者修改服务商
     */
    Integer addOrUpdateFacilitator(FacilitatorDto facilitatorDto);


    /**
     * 获取服务商详情
     */
    FacilitatorDto getFacilitatorDto(IdDto idDto);

    /**
     * 查询全部服务商
     */
    List<FacilitatorDto> getList(SearchFacilitatorDto searchFacilitatorDto);

    /**
     * 删除
     */
    Integer delFacilitator(IdDto idDto);


    List<Facilitator> getListByIds(List<Integer> ids);


}
