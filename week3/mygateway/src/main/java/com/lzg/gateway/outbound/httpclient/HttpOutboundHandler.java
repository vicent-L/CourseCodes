package com.lzg.gateway.outbound.httpclient;

import com.lzg.gateway.common.HttpClientUtil;
import com.lzg.gateway.common.SingletonThreadPool;
import com.lzg.gateway.filter.HeaderHttpResponseFilter;
import com.lzg.gateway.filter.HttpRequestFilter;
import com.lzg.gateway.filter.HttpResponseFilter;
import com.lzg.gateway.router.HttpEndpointRouter;
import com.lzg.gateway.router.PollingHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author lzg
 * @Date 2021-05-22 15:04
 */
public class HttpOutboundHandler {

    Logger log = LoggerFactory.getLogger(HttpOutboundHandler.class);
    private CloseableHttpAsyncClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;

    private HttpResponseFilter filter = new HeaderHttpResponseFilter();

    private HttpEndpointRouter router;


    public HttpOutboundHandler(List<String> backends, HttpEndpointRouter router) {
        // 默认轮询
        if (null == router) {
            this.router = new PollingHttpEndpointRouter();
        } else {
            this.router = router;
        }
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());
        proxyService = SingletonThreadPool.getInstance().getExecutorPool();
        httpclient = HttpClientUtil.getHttpClient();
        httpclient.start();
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String httpInfo = fullRequest.content().toString(CharsetUtil.UTF_8) + fullRequest.uri();
        log.info("请求体：" + fullRequest.content().toString(CharsetUtil.UTF_8));
        String backendUrl = router.route(this.backendUrls, httpInfo);
        final String url = backendUrl + fullRequest.uri();
        log.info("url>>>>>>>>>>>>" + url);
        filter.filter(fullRequest, ctx);
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("xjava", inbound.headers().get("xjava"));

        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    handleResponse(inbound, ctx, endpointResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

            filter.filter(response);

            for (Header e : endpointResponse.getAllHeaders()) {
                //response.headers().set(e.getName(),e.getValue());
                log.info(e.getName() + " => " + e.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
