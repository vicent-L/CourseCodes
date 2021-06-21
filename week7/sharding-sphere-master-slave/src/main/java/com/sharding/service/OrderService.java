package com.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sharding.entity.Order;
import org.springframework.stereotype.Service;

/**
 * @Author lzg
 * @Date 2021-06-21 21:15
 */
@Service
public interface OrderService  extends IService<Order> {

    public boolean insertBatchOrder();

}
