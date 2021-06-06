package com.lzg.spring.jdbc;



import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikaricpUtil {
	public static HikariDataSource dataSource;

	public static HikariDataSource getDataSource() {

		try {
			if (dataSource == null) {
				final String configureFilePath = HikaricpUtil.class.getClassLoader().getResource("hikari.properties").getPath();
				HikariConfig configure = new HikariConfig(configureFilePath);
				dataSource = new HikariDataSource(configure);
			}
			return dataSource;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
