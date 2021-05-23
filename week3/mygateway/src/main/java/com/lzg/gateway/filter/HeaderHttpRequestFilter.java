package com.lzg.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-05-22 10:57
 */
public class HeaderHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("xjava", "do");
    }
}
