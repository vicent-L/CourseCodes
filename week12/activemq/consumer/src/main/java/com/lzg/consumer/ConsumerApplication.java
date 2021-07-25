package com.lzg.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

/**
 * @Author lzg
 * @Date 2021-07-22 11:17
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableJms//开启jms的注解以及适配
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}