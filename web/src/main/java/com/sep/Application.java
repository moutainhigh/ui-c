package com.sep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * TODO
 *
 * @author zhangkai
 * @date 2020/2/3 上午10:19
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.sep")
@MapperScan("com.sep.*.repository")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
