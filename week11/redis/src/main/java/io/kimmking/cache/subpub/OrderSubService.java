package io.kimmking.cache.subpub;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author lzg
 * @Date 2021-07-18 19:34
 */
@Service
@Slf4j
public class OrderSubService {

    public void orderMessage(String message, String channel) {
        log.debug("order message subscribe:{}, channel:{} **********", message, channel);
        switch (channel) {
            case RedisPubSub.SEND_PUB:
                handleOrderService(message);
                break;
            default:
                break;
        }
    }


    public void handleOrderService(String message){
        JSONObject jsonObject = JSONObject.parseObject(message);

        log.debug("订单消息处理： " + jsonObject);
    }

}
