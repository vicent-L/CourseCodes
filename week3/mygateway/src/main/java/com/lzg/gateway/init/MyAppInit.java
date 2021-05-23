package com.lzg.gateway.init;


import com.lzg.gateway.config.ServerConfig;
import com.lzg.gateway.inbound.HttpInboundServer;
import com.lzg.gateway.router.ConsistentHashHttpEndpointRouter;
import com.lzg.gateway.router.PollingHttpEndpointRouter;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lzg
 * @Date 2021-05-22 14:37
 */

@Component
public class MyAppInit implements ApplicationListener<ApplicationStartedEvent> {

    private static Logger log = LoggerFactory.getLogger(MyAppInit.class);


    @Autowired
    private ServerConfig Serverconfig;

    private int port = 9000;

    public void onApplicationEvent(ApplicationStartedEvent event) {

        log.info(" gateway starting...");
        List<String> serverList = Serverconfig.getServerlist();
        String addrPrefix = "http://";
        List<String> serverAddrList = new ArrayList<String>();
        for (String serverInfo : serverList) {
            serverAddrList.add(addrPrefix + serverInfo);
        }
        HttpInboundServer server = new HttpInboundServer(port, serverAddrList, new PollingHttpEndpointRouter());
        log.info("gateway started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
