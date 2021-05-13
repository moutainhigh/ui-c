package com.sep.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.utils.JwtUtils;
import com.sep.content.dto.AddRetransmissionDto;
import com.sep.content.dto.IdDto;
import com.sep.content.enums.ObjType;
import com.sep.content.model.RetransmissionLog;
import com.sep.content.repository.RetransmissionLogMapper;
import com.sep.content.service.ActivityService;
import com.sep.content.service.ArticleService;
import com.sep.content.service.IncreaseIntegral;
import com.sep.content.service.RetransmissionLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-02-09
 */
@Service
public class RetransmissionLogServiceImpl extends ServiceImpl<RetransmissionLogMapper, RetransmissionLog> implements RetransmissionLogService {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ArticleService articleService;

   @Autowired
   private IncreaseIntegral increaseIntegral;

    @Override
    public Integer addRetransmission(AddRetransmissionDto addRetransmissionDto) {
        Integer result = 0;
        String userId = JwtUtils.parseJWT(addRetransmissionDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            RetransmissionLog retransmissionLog = new RetransmissionLog();
            BeanUtils.copyProperties(addRetransmissionDto, retransmissionLog);
            retransmissionLog.setUserId(Integer.parseInt(userId));
            result = retransmissionLog.insert() ? 1 : 0;
            if (result > 0) {
                IdDto idDto = new IdDto();
                idDto.setId(retransmissionLog.getObjId());
                if (retransmissionLog.getObjType().equals(ObjType.ACTIVITY.getCode())) {
                    activityService.plusRetransmission(idDto);
                }
                if (retransmissionLog.getObjType().equals(ObjType.ARTICLE.getCode())) {
                    articleService.plusRetransmissionNum(idDto);
                }
                increaseIntegral.increase(Integer.parseInt(userId),4);
            }
        }
        return result;
    }
}
