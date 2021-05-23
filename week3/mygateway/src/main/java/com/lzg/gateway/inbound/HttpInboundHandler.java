package com.lzg.gateway.inbound;

import com.lzg.gateway.filter.HeaderHttpRequestFilter;
import com.lzg.gateway.filter.HttpRequestFilter;
import com.lzg.gateway.outbound.httpclient.HttpOutboundHandler;
import com.lzg.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> proxyServer;
    private HttpOutboundHandler handler;
    private HttpRequestFilter filter = new HeaderHttpRequestFilter();

    //    public HttpInboundHandler(List<String> proxyServer) {
//        this.proxyServer = proxyServer;
//        this.handler = new HttpOutboundHandler(this.proxyServer);
//    }
    public HttpInboundHandler(List<String> proxyServer, HttpEndpointRouter router) {
        this.proxyServer = proxyServer;
        this.handler = new HttpOutboundHandler(this.proxyServer, router);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            handler.handle(fullRequest, ctx, filter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


}
