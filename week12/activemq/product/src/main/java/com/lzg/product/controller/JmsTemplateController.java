package com.lzg.product.controller;

import com.lzg.product.constants.MQConstant;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author lzg
 * @Date 2021-07-22 16:30
 */
@RestController
@Slf4j
public class JmsTemplateController {


    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;


    @RequestMapping("/sendMessage")
    public void sendMessage(@RequestParam("destination") String destination, @RequestParam("msg") String msg){
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

    }


    /**
     * 向默认队列发送消息
     */
    @RequestMapping("/sendMessage2")
    public void sendMessage(@RequestParam("msg") final String msg) {
        String destination =  jmsTemplate.getDefaultDestination().toString();
        log.info("向默认队列" +destination+ "发送了消息------------" + msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

    }

}
