package com.sharding.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lzg
 * @Date 2021-06-25 16:29
 */
@Data
public class OrderDetail {

    private Long orderDetailId;

    private Long orderId;

    private Integer userId;

    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private Date createTime;

    private Date updateTime;

}
