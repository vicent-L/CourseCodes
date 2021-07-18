package io.kimmking.cache.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author lzg
 * @Date 2021-07-18 15:53
 * stringRedisTemplate.delete(lockKey);
 * 释放锁，如果在这段代码出现异常了的话， 这个锁没有释放掉，别的线程进来的话拿不到锁，会陷入一种死锁状态。
 */
@RestController
@Slf4j
public class StockController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/deduct_stock")
    // 存在并发问题
    public String deductStock() {
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
        } else {
            log.info("扣减失败，库存不足");
        }
        return "end";
    }


    //  Redisson 实现
    @RequestMapping("/reduce_stock")
    public String reduceStock() {
        String lockKey = "lockKey";
        //普通的可重入锁
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            // 拿锁失败时会不停的重试
            // 具有Watch Dog 自动延期机制 默认续30s
            rLock.lock();

//            // 尝试拿锁10s后停止重试,返回false
//            // 具有Watch Dog 自动延期机制 默认续30s
//            boolean res1 = rLock.tryLock(10, TimeUnit.SECONDS);
//
//            // 拿锁失败时会不停的重试  没有Watch Dog ，10s后自动释放
//            rLock.lock(10, TimeUnit.SECONDS);
//
//            // 尝试拿锁50s后停止重试,返回false  没有Watch Dog ，10s后自动释放
//            boolean res2 = rLock.tryLock(50, 10, TimeUnit.SECONDS);
//
//            //  公平锁 保证 Redisson 客户端线程将以 请求 顺序获得锁
//            RLock fairLock = redissonClient.getFairLock("fairLock");
//
//            // 读写锁  与JDK中ReentrantLock的读写锁效果一样
//            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("readWriteLock");
//            readWriteLock.readLock().lock();
//            readWriteLock.writeLock().lock();
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));//jedis.get("stock");
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");//jedis.set(key,value)
                System.out.println("线程id：" + Thread.currentThread().getId() + "： 扣减库存成功，当前库存数：" + realStock );
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            rLock.unlock();
        }
        return "end";
    }

    @RequestMapping("/addStock")
    public String addStock(@RequestParam("id") Integer stock) {
        stringRedisTemplate.opsForValue().set("stock", stock + "");
        return "ok";
    }
}


