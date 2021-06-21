package com.sharding.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sharding.entity.Order;
import com.sharding.mapper.OrderMapper;
import com.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用mybatis plus 批量插入
 * 每次批量数1000时候 耗时223s
 * 配置文件 url 加参数rewriteBatchedStatements=true  耗时35s
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderService orderService;


    public boolean insertBatchOrder() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        List<Order> entityList = new ArrayList<Order>(1000);
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < 10000; i++) {
                Order order = new Order();
                long orderId = snowflake.nextId();
                long orderNo = snowflake.nextId();
                order.setOrderId(orderId);
                order.setOrderNo(orderNo);
                order.setAddressId(1);
                order.setOrderAmount(new BigDecimal(5.3));
                order.setOrderStatus(1);
                order.setPaymentMethod(3);
                order.setPayStatus(3);
                order.setUserId(6);
                order.setCreateTime(DateUtil.date());
                order.setUpdateTime(DateUtil.date());
                entityList.add(order);
            }
        }

        long begin = System.currentTimeMillis();
        System.out.println("插入订单数据开始" + begin);
        boolean result = orderService.saveBatch(entityList, 50000);
        long end = System.currentTimeMillis();
        System.out.println("插入订单数据结束" + end);
        long total = (end - begin) / 1000;

        System.out.println("耗时：" + total);

        return result;
    }
}
