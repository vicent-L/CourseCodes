package io.kimmking.cache.redislock;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lzg
 * @Date 2021-07-18 11:08
 */
public class RedisLockTest {
    private static RedisLockUtil demo = new RedisLockUtil();

    private static final AtomicInteger count = new AtomicInteger(100);

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                UUID uuid = UUID.randomUUID();
                String id = uuid + ":" + Thread.currentThread().getId();
                boolean isLock = demo.lock(id);
                try {
                    // 拿到锁的话，就对共享参数减一
                    if (isLock) {

                        System.out.println( count.decrementAndGet());
                    }
                } finally {
                    // 释放锁一定要注意放在finally
                    demo.unlock(id);
                }
            }).start();
        }
    }
}
