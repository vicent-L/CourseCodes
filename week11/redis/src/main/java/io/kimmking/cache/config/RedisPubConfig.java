package io.kimmking.cache.config;

import io.kimmking.cache.subpub.OrderSubService;
import io.kimmking.cache.subpub.RedisPubSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lzg
 * @Date 2021-07-18 19:36
 * <p>
 * redis发布订阅配置类
 */
@Configuration
public class RedisPubConfig {

    @Resource
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private OrderSubService orderSubService;

    @Bean
    public RedisMessageListenerContainer container(  MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        List<PatternTopic> topicList = new ArrayList<PatternTopic>(10);
        topicList.add(new PatternTopic(RedisPubSub.SEND_PUB));
        container.addMessageListener(listenerAdapter, topicList);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(orderSubService, RedisPubSub.INVOKE_METHOD);
    }

}
