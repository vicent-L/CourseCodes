package com.sharding.dao;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

/**
 * @Author lzg
 * @Date 2021-06-23 22:45
 */
@Slf4j
public class OrderDaoImpl implements IOrderDao {

    private DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void insertOrder() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into order_master(order_id,order_no,user_id, address_id, order_amount,payment_method,order_status,pay_status,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            long orderId = snowflake.nextId();
            long orderNo = snowflake.nextId();
            //    设置占位符对应的参数
            ps.setLong(1, orderId);
            ps.setLong(2, orderNo);
            ps.setInt(3, 8);
            ps.setInt(4, 33);
            ps.setBigDecimal(5, new BigDecimal(6.8));
            ps.setInt(6, 2);
            ps.setInt(7, 1);
            ps.setInt(8, 2);
            ps.setTimestamp(9, new Timestamp(new java.util.Date().getTime()));
            ps.setTimestamp(10, new Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            close(con, ps, null);

        }

    }

    public void queryOrder() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            String sql = "select * from order_master ";
            statement = conn.prepareStatement(sql);

            rs = statement.executeQuery();

            while (rs.next()) {
                log.info(rs.getBigDecimal("order_id") + " " + rs.getInt("user_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {

            close(conn, statement, rs);

        }
    }

    public static void close(Connection con, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
