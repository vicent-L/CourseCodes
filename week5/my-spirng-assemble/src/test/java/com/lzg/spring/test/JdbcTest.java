package com.lzg.spring.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author lzg
 * @Date 2021-06-05 23:28
 */
public class JdbcTest {

    @Test
    public void hikariTest() throws Exception {
//        final String configureFile = "src/main/resources/hikari.properties";
//        final String configureFile =JdbcTest.class.getClassLoader().getResource("hikari.properties").getPath();
        final String configureFile = getClass().getClassLoader().getResource("hikari.properties").getPath();
        HikariConfig configure = new HikariConfig(configureFile);
        HikariDataSource dataSource = new HikariDataSource(configure);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
