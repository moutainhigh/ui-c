package com.sep.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.content.dto.AddRetransmissionDto;
import com.sep.content.model.RetransmissionLog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-09
 */
public interface RetransmissionLogService extends IService<RetransmissionLog> {


    /**
     * 添加转发日志
     * 添加成功后根据类型更新转发数量
     */
    Integer addRetransmission(AddRetransmissionDto addRetransmissionDto);


}
