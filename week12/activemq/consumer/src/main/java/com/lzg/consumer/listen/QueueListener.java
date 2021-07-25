package com.lzg.consumer.listen;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-07-22 11:18
 * queue为点对点模式；
 */

@Component
public class QueueListener {

    @JmsListener(destination = "publish.queue", containerFactory = "queueListenerFactory")
    @SendTo("out.queue")
    public String receive(String text){
        System.out.println("QueueListener: consumer 收到一条信息: " + text);
        return "consumer-a received : " + text;
    }
}