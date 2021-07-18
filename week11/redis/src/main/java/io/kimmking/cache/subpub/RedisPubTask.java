package io.kimmking.cache.subpub;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author lzg
 * @Date 2021-07-18 19:39
 */

@Configuration
@EnableScheduling
public class RedisPubTask {


    @Scheduled(fixedDelay = 10000 )
    private void configureTasks() {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "192.168.1.104", 6379,0);
        RedisPublisher redisPublisher = new RedisPublisher(jedisPool);
        redisPublisher.sendMsg();
    }
}