package io.kimmking.javacourse.kafka.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lzg
 * @Date 2021-08-01 21:18
 */
@RestController
public class KafkaProductor {

    @Autowired
    private KafkaTemplate template;

    @RequestMapping("/sendMessage")
    public String sendMsg(String topic, String message){
        template.send(topic,message);
        return "success";
    }
}
