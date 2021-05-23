package com.lzg.gateway.common;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

/**
 * @Author lzg
 * @Date 2021-05-23 14:48
 */
public class HttpClientUtil {
    private static  CloseableHttpAsyncClient httpclient=null;


    public static synchronized CloseableHttpAsyncClient getHttpClient(){
        int cores = Runtime.getRuntime().availableProcessors();
        if(httpclient == null){
            IOReactorConfig ioConfig = IOReactorConfig.custom()
                    .setConnectTimeout(1000)
                    .setSoTimeout(1000)
                    .setIoThreadCount(cores)
                    .setRcvBufSize(32 * 1024)
                    .build();

            httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                    .setMaxConnPerRoute(8)
                    .setDefaultIOReactorConfig(ioConfig)
                    .setKeepAliveStrategy((response, context) -> 6000)
                    .build();

        }
        return httpclient;
    }

}
