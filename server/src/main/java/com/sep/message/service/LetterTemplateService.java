package com.sep.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.message.dto.DisableLetterTemplateDto;
import com.sep.message.dto.EnableLetterTemplateDto;
import com.sep.message.dto.PageSearchLetterTemplateDto;
import com.sep.message.dto.SendLetterInput;
import com.sep.message.model.LetterTemplate;
import com.sep.message.vo.LetterTemplateVo;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
public interface LetterTemplateService extends IService<LetterTemplate> {

    /**
     * 分页查询
     * @param dto 请求参数
     * @return 详情
     */
    IPage<LetterTemplateVo> pageSearch(PageSearchLetterTemplateDto dto);

    /**
     * 详情查询
     * @param id ID
     * @return 详情
     */
    LetterTemplateVo findById(Integer id);

    /**
     * 开启
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean enable(EnableLetterTemplateDto dto);

    /**
     * 关闭
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean disable(DisableLetterTemplateDto dto);

    /**
     * 发送信件
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean send(SendLetterInput dto);

}
