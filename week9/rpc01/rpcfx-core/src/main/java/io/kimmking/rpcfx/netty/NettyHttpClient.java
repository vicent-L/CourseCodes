package io.kimmking.rpcfx.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * @Author lzg
 * @Date 2021-07-03 15:01
 * 同步发送接收数据
 */
public class NettyHttpClient {

    private ClientResponseHandler clientHandler;

    private NettyHttpClient() {
        clientHandler = new ClientResponseHandler();
    }

    private static class SingletonInstance {
        private static final NettyHttpClient INSTANCE = new NettyHttpClient();
    }

    public static NettyHttpClient getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void connect(String url) throws Exception {
        URI uri = new URI(url);
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(loopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpRequestEncoder()).addLast(new HttpResponseDecoder()).addLast(clientHandler);
            }
        });
        System.out.println(uri.getHost() + "====" + uri.getPort());

        Channel channel = b.connect(uri.getHost(), uri.getPort() < 0 ? 80 : uri.getPort()).sync().channel();
        while (!channel.isActive()) {
            Thread.sleep(2000);
        }
    }


    public String getPostResult(URI uri, String content) throws Exception {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        clientHandler.sendMessage(request);
        return clientHandler.getData();
    }
}
