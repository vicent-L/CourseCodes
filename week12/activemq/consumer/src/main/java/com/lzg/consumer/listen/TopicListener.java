package com.lzg.consumer.listen;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-07-22 11:19
 * 订阅模式
 * 使用JmsListener注解中的containerFactory属性，可以配置spring.jms.pub-sub属性，实现同时接收queque和topic；
 */

@Component
public class TopicListener {

    @JmsListener(destination = "publish.topic", containerFactory = "topicListenerFactory")
    public void receive(String msg) {
        System.out.println("TopicListener: consumer-1收到一条信息: " + msg);
    }

    // 使用JmsListener配置消费者监听的队列
    @JmsListener(destination = "publish.topic", containerFactory = "topicListenerFactory")
    public void receive2(String msg) {
        System.out.println("TopicListener: consumer-2收到一条信息：" + msg);
    }

}