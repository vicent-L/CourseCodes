package com.lzg.spring.assemble.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lzg
 * @Date 2021-06-05 14:36
 * 通过java配置类方式装配bean
 */
@Configuration
public class MyJavaConfig {

    @Bean
    public User getUser(){
       User user = new User();
        user.setUsername("lsp");
        user.setPassword("666");
        return user;
    }
}
