package io.kimmking.javacourse.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-08-01 21:20
 */
@Component
public class KafkaConsumer {
    @KafkaListener(topics = {"lzg"})
    public void listen(ConsumerRecord record){
        System.out.println("消费者消费消息："+record.topic()+":"+record.value());
    }
}