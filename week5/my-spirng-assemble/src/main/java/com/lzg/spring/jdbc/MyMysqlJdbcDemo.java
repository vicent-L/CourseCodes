package com.lzg.spring.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @Author lzg
 * @Date 2021-06-05 18:30
 */
public class MyMysqlJdbcDemo {
    private static Logger logger = LoggerFactory.getLogger(MyMysqlJdbcDemo.class);
    public static final String URL = "jdbc:mysql://192.168.1.103:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    public static final String USERNAME= "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) {

//            query();
//        delete();
//        handleDB();
//        add();
//            update("321",1);

//            delete(2);
//             delete(); // 删除全表数据
                addBatch();
    }


    public static void add() {
        Connection con = null;
        PreparedStatement ps= null;
        String sql = "insert into user_info(id, username, password,openid,role,create_time,update_time) values(?,?,?,?,?,?,?)";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setInt(1, 2);
            ps.setString(2,  "s3swx");
            ps.setString(3, "2333222332");
            ps.setString(4,  "cfrvgrfesfefe");
            ps.setInt(5, 2);
            ps.setTimestamp(6,new Timestamp(new java.util.Date().getTime()));
            ps.setTimestamp(7,new Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch(Exception e){
           e.printStackTrace();
           logger.error(e.getMessage());
        } finally {
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 批处理语句
    public static void addBatch(){
        Connection con = null;
        PreparedStatement ps= null;
        String sql = "insert into user_info(id, username, password,openid,role,create_time,update_time) values(?,?,?,?,?,?,?)";
        try {
//            con = getConnection();
            //连接池
            con =HikaricpUtil.getDataSource().getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 1; i <=1200 ; i++) {
                //j将sql添加到批处理中
                ps.setInt(1,i);
                ps.setString(2,  "xyz");
                ps.setString(3, "6683");
                ps.setString(4,  "lgrgstyjs");
                ps.setInt(5, 2);
                ps.setTimestamp(6,new Timestamp(new java.util.Date().getTime()));
                ps.setTimestamp(7,new Timestamp(new java.util.Date().getTime()));
                ps.addBatch();
                if(i%500==0){
                    int[] results = ps.executeBatch();
                    ps.clearBatch();
                    System.out.println("1--批量添加长度为:"+results.length);
                }
            }
            int[] res=ps.executeBatch();
            System.out.println("2--批量添加长度为:"+ res.length);
            ps.clearBatch();
        } catch (Exception e ){
            e.printStackTrace();
        } finally {
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    // 事务支持   模拟插入并更新操作
    public static void handleDB(){
        Connection con = null;
        PreparedStatement ps= null;
        PreparedStatement ps2= null;
        String sql = "insert into user_info(id, username, password,openid,role,create_time,update_time) values(?,?,?,?,?,?,?)";
        // 更新操作
        String sql2 ="update user_info set password = ?  where id = ?";
        try {
            con = getConnection();
            //取消自动提交事务
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setInt(1, 100);
            ps.setString(2,  "frer");
            ps.setString(3, "656");
            ps.setString(4,  "nmhhggnh");
            ps.setInt(5, 2);
            ps.setTimestamp(6,new Timestamp(new java.util.Date().getTime()));
            ps.setTimestamp(7,new Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();

            //
            //模拟发生 异常现象
            System.out.println(1/0);
            // 需要保证拿到同一个连接
            ps = con.prepareStatement(sql2);
            ps.setString(1, "22");
            ps.setInt(2,101 );
            ps.executeUpdate();
            // 更新 插入操作都完成才提交事务
            con.commit();
        } catch(Exception e){
            try {
                //执行事务回滚操作
                con.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(String password, Integer id)   {
        Connection con = null;
        PreparedStatement ps= null;   //  预编译对象
        String sql = "update user_info set password = ?  where id = ?";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, password);
            ps.setInt(2,id );
            ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static void query() {
        Connection conn =null;
        PreparedStatement statement= null;
        ResultSet rs=null;
        try {
            conn =  getConnection();
            String sql = "select * from user_info where username= ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "lzg");
            rs = statement.executeQuery();

            while (rs.next()) {
                logger.info(rs.getString("username") + " " + rs.getString("openid"));
            }

        } catch (Exception e){
            e.printStackTrace();
           logger.error(e.getMessage());
        } finally{
            try {
                close(conn,statement,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(int id) {
        Connection con = null;
        PreparedStatement ps= null;   //  预编译对象
        String sql = "delete from user_info where id = ?";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (Exception e){
           e.printStackTrace();
        }  finally{
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete() {
        Connection con = null;
        PreparedStatement ps= null;   //  预编译对象
        String sql = "delete from user_info  ";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            int result = ps.executeUpdate();
            logger.info("删除条数"+result);
        }catch (Exception e){
            e.printStackTrace();
        }  finally{
            try {
                close(con, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return con;
    }

    public static void close(Connection con, Statement st, ResultSet rs) throws  SQLException{
        if(rs != null) {
            rs.close();
        }
        if(st != null) {
            st.close();
        }
        if(con != null) {
            con.close();
        }
    }
}
