package io.kimmking.cache.subpub;

/**
 * @Author lzg
 * @Date 2021-07-18 19:32
 */
public interface RedisPubSub {

    /**
     * 订阅处理方法
     */
    String INVOKE_METHOD = "orderMessage";


    /**
     * 订阅频道:处理打卡记录
     */
    String SEND_PUB = "order:pub";

}
