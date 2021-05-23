package com.lzg.gateway.common;

import com.lzg.gateway.outbound.httpclient.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * @Author lzg
 * @Date 2021-05-23 14:02
 */
public class SingletonThreadPool {

    private ExecutorService executorService;

    private static class SingletonHolder {
        private static SingletonThreadPool instance = new SingletonThreadPool();
    }

    private SingletonThreadPool() {
        if (null == executorService) {
            int cores = Runtime.getRuntime().availableProcessors();
            long keepAliveTime = 1000;
            int queueSize = 2048;
            RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
            executorService = new ThreadPoolExecutor(cores, cores,
                    keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                    new NamedThreadFactory("proxyService"), handler);
        }
    }

    public ExecutorService getExecutorPool(){
        return executorService;
    }
    public static SingletonThreadPool getInstance() {
        return SingletonHolder.instance;
    }

}

