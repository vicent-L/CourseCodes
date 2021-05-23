package com.lzg.gateway.router;

import java.util.List;


public interface HttpEndpointRouter {

    String route(List<String> endpoints,String httpInfo);

    // Load Balance
    // Random
    // RoundRibbon
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50

}