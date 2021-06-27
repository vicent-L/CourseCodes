package com.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lzg
 * @Date 2021-06-21 20:56
 */
@Data
//@TableName("order_master")
public class Order {
    @TableId("order_id")
    private Long orderId;

    private Long orderNo;

    private Integer userId;

    private Integer addressId;

    private BigDecimal orderAmount;

    private Integer paymentMethod;

    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;


}
