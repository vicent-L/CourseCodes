package com.lzg.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Author lzg
 * @Date 2021-05-22 11:09
 */
public class HeaderHttpResponseFilter  implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
    }
}
