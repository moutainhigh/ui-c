package com.sep.sku.service.impl;

import com.sep.sku.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@Slf4j
public class SkuTask {

    @Autowired
    private SkuInfoService skuInfoService;

    @Scheduled(fixedDelay = 5000)
    private void configureTasks() {
//        log.info(" sku  status action---------------------------------- {}", LocalDateTime.now());
//        skuInfoService.taskUpdateSkuStatus();
//        log.info(" sku  status end---------------------------------- {}", LocalDateTime.now());
    }

}
