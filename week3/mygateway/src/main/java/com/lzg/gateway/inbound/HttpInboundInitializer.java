package com.lzg.gateway.inbound;

import com.lzg.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;



public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServer;
    private HttpEndpointRouter httpEndpointRouter;

    public HttpInboundInitializer(List<String> proxyServer, HttpEndpointRouter httpEndpointRouter) {
        this.proxyServer = proxyServer;
        this.httpEndpointRouter = httpEndpointRouter;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpInboundHandler(this.proxyServer,httpEndpointRouter));


    }
}
