
package com.lzg.gateway.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @Author lzg
 * @Date 2021-05-22 19:45
 * 一致性hash
 */

public class ConsistentHashHttpEndpointRouter implements HttpEndpointRouter{

    Logger log = LoggerFactory.getLogger(ConsistentHashHttpEndpointRouter.class);
    private static SortedMap<Long, String> virtualNodes = new TreeMap<>();
    private static final int VIRTUAL_NODES = 6000;
    @Override
    public String route(List<String> endpoints,String httpInfo) {
        for (String client:endpoints){
            log.info("ConsistentHash-endpoints"+client);
        }
        addVisualNodes(endpoints);
        long hash = FNVHash(httpInfo);
        SortedMap<Long, String> subMap = virtualNodes.tailMap(hash);
        Long nodeIndex = subMap.firstKey();
        if (nodeIndex == null) {
            nodeIndex = virtualNodes.firstKey();
        }
        return subMap.get(nodeIndex);

    }

    private void addVisualNodes(List<String>  urls) {
        for (String httpUrl : urls) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                long hash = FNVHash(httpUrl + "*" + i);
                virtualNodes.put(hash, httpUrl);
            }
        }

    }

    // 32位的 Fowler-Noll-Vo 哈希算法
    private static Long FNVHash(String key) {
        final int p = 16777619;
        Long hash = 2166136261L;
        for (int idx = 0, num = key.length(); idx < num; ++idx) {
            hash = (hash ^ key.charAt(idx)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

}
