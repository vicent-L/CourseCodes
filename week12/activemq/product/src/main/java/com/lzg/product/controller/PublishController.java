package com.lzg.product.controller;

import com.lzg.product.constants.MQConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Author lzg
 * @Date 2021-07-22 11:15
 * 发送的消息为对象的时候，需要将对象序列化；消费者接收对象信息时需要使用ObjectMessage进行转化；
 */

@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    @Qualifier(MQConstant.JMS_MESSAGE_QUEUE_TEMPLATE)
    private JmsMessagingTemplate jms;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @RequestMapping("/queue")
    public String queue(){

        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(queue, "queue"+i);
        }

        return "queue 发送成功";
    }

    //注意这里containerFactory改为MQConstant.TOPIC_LISTENER_CONTAINER_FACTORY 订阅模式是收不到确认消息的
    @JmsListener(destination = "out.queue", containerFactory = MQConstant.QUEUE_LISTENER_CONTAINER_FACTORY)
    public void consumerMsg(String msg){
        System.out.println("队列 out.queue 获取消息："+msg);
    }

    @RequestMapping("/topic")
    public String topic(){

        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(topic, "topic"+i);
        }

        return "topic 发送成功";
    }
}