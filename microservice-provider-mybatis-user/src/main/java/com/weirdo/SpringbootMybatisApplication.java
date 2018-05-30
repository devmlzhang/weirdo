package com.weirdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */

@EnableDiscoveryClient
@MapperScan("com.weirdo.dao")
@SpringBootApplication
@ImportResource({"classpath:config/spring_mqtt.xml"})
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }
}
