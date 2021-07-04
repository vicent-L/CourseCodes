package io.kimmking.rpcfx.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * @Author lzg
 * @Date 2021-07-03 14:55
 */
public class ClientResponseHandler  extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data;

    private CountDownLatch countDownLatch = new CountDownLatch(1);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

    public String getData() throws InterruptedException {
        countDownLatch.await();
        return data;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse)
        {
            HttpResponse response = (HttpResponse) msg;
            HttpResponseStatus status = response.getStatus();
            int code = status.code();
            System.out.println("http response code : " +code);
            HttpHeaders headers = response.headers();
            Iterator<String> hiterator = headers.names().iterator();
            while(hiterator.hasNext()){
                String str = (String) hiterator.next();
                System.out.println("响应头名称： "+str);
            }
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf bf = content.content();

            byte[] byteArray = new byte[bf.capacity()];
            bf.readBytes(byteArray);
            data = new String(byteArray);
            countDownLatch.countDown();

            bf.release();
        }
    }

}
