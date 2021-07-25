package com.lzg.consumer.listen;

/**
 * @Author lzg
 * @Date 2021-07-22 16:34
 */
import com.lzg.consumer.constants.MQConstant;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@EnableJms
public class AutoConsumerListener implements MessageListener {

    @Override
    @JmsListener(destination = "demoQueueDestination1",containerFactory = MQConstant.QUEUE_LISTENER_CONTAINER_FACTORY)
    public void onMessage(Message message) {
        String msg = null;
        try {
            msg = ((TextMessage)message).getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("AutoConsumerListener listen:"+msg);
    }
}
