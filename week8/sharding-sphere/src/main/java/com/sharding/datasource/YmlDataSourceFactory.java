package com.sharding.datasource;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author lzg
 * @Date 2021-06-23 22:34
 */
public class YmlDataSourceFactory {

    public static DataSource getDataSource() throws SQLException, IOException {
//        return YamlShardingSphereDataSourceFactory.createDataSource(
//                getFile("/META-INF/sharding-data.yaml"));

        return YamlShardingSphereDataSourceFactory.createDataSource(
                getFile("/META-INF/sharding-databases-tables.yaml"));
    }

    private static File getFile(final String fileName) {
        return new File(Thread.currentThread().getClass().getResource(fileName).getFile());
    }
}
