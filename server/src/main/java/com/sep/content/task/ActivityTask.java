package com.sep.content.task;

import com.sep.content.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@Slf4j
public class ActivityTask {

    @Autowired
    private ActivityService activityService;


//    @Scheduled(fixedDelay = 5000)
//    private void configureTasks() {
//        log.info(" Activity  status action---------------------------------- {}",LocalDateTime.now());
//        activityService.updateActivityStatus();
//        log.info(" Activity  status end---------------------------------- {}",LocalDateTime.now());
//    }


}
