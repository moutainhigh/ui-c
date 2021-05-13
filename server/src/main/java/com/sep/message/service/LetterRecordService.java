package com.sep.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.message.dto.MyLetterDto;
import com.sep.message.dto.PageSearchLetterRecordDto;
import com.sep.message.dto.ReadLetterDto;
import com.sep.message.model.LetterRecord;
import com.sep.message.vo.LetterRecordVo;
import com.sep.message.vo.MyLetterRecordVo;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
public interface LetterRecordService extends IService<LetterRecord> {

    /**
     * 分页查询
     * @param dto 请求参数
     * @return 详情
     */
    IPage<LetterRecordVo> pageSearch(PageSearchLetterRecordDto dto);

    /**
     * 详情查询
     * @param id ID
     * @return 详情
     */
    LetterRecordVo findById(Integer id);

    /**
     * 我的信件
     *
     * @param dto 请求参数
     * @return 详情
     */
    IPage<MyLetterRecordVo> myLetters(MyLetterDto dto);

    /**
     * 是否有未读信件
     *
     * @param
     * @return 详情
     */
    Integer isRead(Integer userId);

    /**
     * 读取消息
     *
     * @param dto 请求参数
     * @return 详情
     */
    MyLetterRecordVo readLetter(ReadLetterDto dto);

}