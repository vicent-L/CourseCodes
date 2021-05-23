package com.lzg.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lzg
 * @Date 2021-05-22 16:44
 */
@Configuration
@ConfigurationProperties(prefix = "config.server")
@Data
public class ServerConfig {

        // 服务器ip端口
        private List<String> serverlist = new ArrayList<>();
    }

