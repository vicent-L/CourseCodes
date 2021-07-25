package com.lzg.product;

/**
 * @Author lzg
 * @Date 2021-07-22 14:11
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

//@EnableScheduling//开启定时任务
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableJms//开启jms的注解以及适配
public class ProductApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ProductApplication.class,args);
        SpringApplication application = new SpringApplication(ProductApplication.class);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }


}