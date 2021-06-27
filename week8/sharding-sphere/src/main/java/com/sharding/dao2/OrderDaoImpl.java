package com.sharding.dao2;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.log.StaticLog;
import com.sharding.entity.Order;
import com.sharding.entity.OrderDetail;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lzg
 * @Date 2021-06-25 14:42
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void selectOrder() {
        String selectOrderSql = "select * from t_order join t_order_detail on t_order.order_id=t_order_detail.order_id order by desc";
        List result = jdbcTemplate.query(selectOrderSql, new RowMapper<Map>() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map row = new HashMap();
                row.put("user_id",rs.getInt("user_id"));
                row.put("order_id",rs.getLong("order_id"));
                return row;
            }});

        System.out.println(result.toArray());
        System.out.println("查询结果集大小: "+result.size());
    }

    @Override
    public List<Long> insertOrder() {
        List<Long> result = new ArrayList<Long>(10);
        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {
            //关闭自动提交
            connection.setAutoCommit(false);
            try {
                for (int i = 1; i <= 5; i++) {
                    Order orderInfo = createOrderInfo(i);
                    insertOrderInfo(orderInfo, connection);

                    System.out.println("---------------------------------------");
                    OrderDetail orderDetail = createOrderDetailInfo(i, orderInfo);

                    //插入详情表信息
                    insertOrderDetail(orderDetail, connection);
                    result.add(orderDetail.getOrderDetailId());


                    //模拟异常
//                    if (i == 3) {
//                        throw new SQLException("exception occur!");
//                    }

                }

                connection.commit();
            } catch (final SQLException ex) {
                connection.rollback();
                throw ex;
            }

            return connection;
        });


        return result;
    }


    public OrderDetail createOrderDetailInfo(int i, Order order) {
//        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
//        long orderDetailNo = snowflake.nextId();
        OrderDetail orderDetail = new OrderDetail();
        //主键
//        orderDetail.setOrderDetailId(orderDetailNo);
        orderDetail.setOrderId(order.getOrderId());

        orderDetail.setUserId(i);

        orderDetail.setProductId(3);
        orderDetail.setProductName("pd" + i);
        orderDetail.setProductPrice(new BigDecimal(3.6));

        orderDetail.setProductQuantity(6);
        orderDetail.setCreateTime(DateUtil.now());

        orderDetail.setUpdateTime(DateUtil.now());
        return orderDetail;


    }

    public Order createOrderInfo(int i) {
        Order order = new Order();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long orderNo = snowflake.nextId();

//        ps.setLong(1, orderId);
        order.setOrderNo(orderNo);
        order.setUserId(i);
        order.setPayStatus(1);
        order.setPaymentMethod(1);
        order.setOrderStatus(2);
        order.setAddressId(3);

        order.setOrderAmount(new BigDecimal(9.8));
        order.setCreateTime(DateUtil.now());
        order.setUpdateTime(DateUtil.now());
        return order;
    }


    private void insertOrderInfo(Order order, Connection connection) throws SQLException {

        String insert_order_info_sql = "insert into t_order(order_no,user_id, address_id, order_amount,payment_method,order_status,pay_status,create_time,update_time) values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(insert_order_info_sql, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, order.getOrderNo());
        ps.setInt(2, order.getUserId());
        ps.setInt(3, order.getAddressId());
        ps.setBigDecimal(4, order.getOrderAmount());
        ps.setInt(5, order.getPaymentMethod());
        ps.setInt(6, order.getOrderStatus());
        ps.setInt(7, order.getPayStatus());
        ps.setTimestamp(8, new Timestamp(order.getCreateTime().getTime()));
        ps.setTimestamp(9, new Timestamp(order.getUpdateTime().getTime()));
        ps.executeUpdate();

        ResultSet resultSet = ps.getGeneratedKeys();
        if (resultSet.next()) {
            StaticLog.info("返回的订单表主键id：{}", resultSet.getLong(1));
            order.setOrderId(resultSet.getLong(1));

        }
    }

    private void insertOrderDetail(OrderDetail orderDetail, Connection connection) throws SQLException {

        String insert_order_info_sql = "insert into t_order_detail(order_id,user_id, product_id, product_name,product_price,product_quantity,create_time,update_time) values(?,?,?,?,?,?,?,?)";

        //需要拿到插入返回主键
        PreparedStatement ps = connection.prepareStatement(insert_order_info_sql, Statement.RETURN_GENERATED_KEYS);
//        ps.setLong(1, orderDetail.getOrderDetailId());
        ps.setLong(1, orderDetail.getOrderId());

        ps.setLong(2, orderDetail.getUserId());

        ps.setInt(3, orderDetail.getProductId());
        ps.setString(4, orderDetail.getProductName());
        ps.setBigDecimal(5, orderDetail.getProductPrice());

        ps.setInt(6, orderDetail.getProductQuantity());

        ps.setTimestamp(7, new Timestamp(orderDetail.getCreateTime().getTime()));
        ps.setTimestamp(8, new Timestamp(orderDetail.getUpdateTime().getTime()));
        ps.executeUpdate();

        ResultSet resultSet = ps.getGeneratedKeys();
        if (resultSet.next()) {
            StaticLog.info("返回的订单明细表主键id：{}", resultSet.getLong(1));
            orderDetail.setOrderDetailId(resultSet.getLong(1));

        }
    }


}
