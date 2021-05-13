package com.sep.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.message.dto.*;
import com.sep.message.model.SystemMessage;
import com.sep.message.vo.MySystemMessageVo;
import com.sep.message.vo.SystemMessageVo;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
public interface SystemMessageService extends IService<SystemMessage> {

    /**
     * 分页查询
     *
     * @param dto 请求参数
     * @return 详情
     */
    IPage<SystemMessageVo> pageSearch(PageSearchSystemMessageDto dto);

    /**
     * 添加
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean add(AddSystemMessageDto dto);

    /**
     * 修改
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean update(UpdateSystemMessageDto dto);

    /**
     * 发布
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean publish(PublishSystemMessageDto dto);

    /**
     * 撤回
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean suspended(SuspendedSystemMessageDto dto);

    /**
     * 删除
     *
     * @param dto 请求参数
     * @return 是否成功
     */
    boolean delete(BaseUpdateDto dto);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 详情
     */
    SystemMessageVo findById(Integer id);

    /**
     * 我的消息
     *
     * @param dto 请求参数
     * @return 详情
     */
    IPage<MySystemMessageVo> myMessages(MySystemMessageDto dto);

    /**
     * 读取消息
     *
     * @param dto 请求参数
     * @return 详情
     */
    MySystemMessageVo readMessage(ReadMessageDto dto);

    Boolean isRead(Integer userId);

}