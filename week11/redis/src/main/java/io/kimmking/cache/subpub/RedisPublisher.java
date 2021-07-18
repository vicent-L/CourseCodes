package io.kimmking.cache.subpub;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @Author lzg
 * @Date 2021-07-18 19:33
 */
public class RedisPublisher {
    private final JedisPool jedisPool;

    public RedisPublisher(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void sendMsg(){
        JSONObject json = new JSONObject();
        json.put("order_id", UUID.randomUUID());
        json.put("name","lzd");
        json.put("time","2021-06-10");
        json.put("address","广东省深圳市");
        json.put("phone","342332525");

        Jedis jedis = jedisPool.getResource();

        jedis.publish(RedisPubSub.SEND_PUB, json.toString());
    }

}
