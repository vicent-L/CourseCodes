package com.sharding.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author lzg
 * @Date 2021-06-25 11:37
 */
@Configuration
@EnableTransactionManagement
public class JdbcTemplateTranscationConfig {

    @Primary
    @Bean("dataSource")
    public  DataSource getDataSource() throws SQLException, IOException {
//        return YamlShardingSphereDataSourceFactory.createDataSource(
//                getFile("/META-INF/sharding-data.yaml"));

        return YamlShardingSphereDataSourceFactory.createDataSource(
                getFile("/META-INF/sharding-databases-tables.yaml"));


    }

    private static File getFile(final String fileName) {
        return new File(Thread.currentThread().getClass().getResource(fileName).getFile());
    }


    @Bean
    public PlatformTransactionManager txManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
