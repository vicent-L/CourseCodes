package com.lzg.gateway.router;

import java.util.List;

/**
 * @Author lzg
 * @Date 2021-05-22 15:42
 * 轮询
 */
public class PollingHttpEndpointRouter implements HttpEndpointRouter {

    private static Integer pos = 0;

    @Override
    public String route(List<String> endpoints, String httpInfo) {
        String rountAddr = null;
        int size = endpoints.size();
        synchronized (pos) {
            if (pos >= size) {
                pos = 0;
            }
            rountAddr = endpoints.get(pos);
            pos++;

        }
        return rountAddr;
    }
}
