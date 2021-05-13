package com.sep.content.service;

import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IncreaseIntegral {

    @Autowired
    private PointService pointService;

    @Value("${point}")
    private Integer point;


    public void increase(Integer userId, Integer type) {
        if (point > 0) {
            PointIncreaseInput pointIncreaseInput = new PointIncreaseInput();
            pointIncreaseInput.setUserId(userId);
            pointIncreaseInput.setFundChangeType(type);
            pointService.increase(pointIncreaseInput);
        }
    }


}
