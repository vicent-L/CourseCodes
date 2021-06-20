package com.lzg.multi.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lzg
 * @Date 2021-06-20 15:04
 */
@Configuration
public class MultiDataSourceConfig {

    //主数据源配置
//    @Primary
    @Bean(name = "ds1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }

    //主数据源
//    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSourceProperties ds2DataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("slaveDataSource")
    public DataSource slaveDataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceName.MASTER, masterDataSource);
        targetDataSources.put(DataSourceName.SLAVE, slaveDataSource);
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }


}
