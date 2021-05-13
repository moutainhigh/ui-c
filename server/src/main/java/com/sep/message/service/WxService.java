package com.sep.message.service;

import com.sep.message.model.LetterRecord;
import com.sep.message.model.LetterTemplate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author litao
 * @since 2020-02-15
 */
public interface WxService {

    void sendMessage(LetterTemplate letterTemplate, List<LetterRecord> letterRecords, Map<String, Object> message);

}