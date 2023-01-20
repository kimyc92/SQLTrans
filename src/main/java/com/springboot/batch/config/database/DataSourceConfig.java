package com.springboot.batch.config.database;

import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import com.springboot.batch.config.util.AES256Util;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Component
@Slf4j
public class DataSourceConfig {

	private static String key = "my_key";

	@Setter
	@Getter
	private String jdbcUrl;

	@Setter
	@Getter
	private String driverClassName;

	@Setter
	@Getter
	private String username;

	@Setter
	@Getter
	private String password;

	public void setDataSourceConfig(String jdbcUrl, String driverClassName
			, String username, String password){
		this.setJdbcUrl(jdbcUrl);
		this.setDriverClassName(driverClassName);
		this.setUsername(username);
		this.setPassword(password);
	}

	public static DataSource createHikariDataSource(Environment env, String db) {
        HikariDataSource dataSource = new HikariDataSource();
        try {

            dataSource.setJdbcUrl(env.getProperty(db+".jdbcUrl"));
            dataSource.setDriverClassName(env.getProperty(db+".driverClassName"));
			dataSource.setUsername(AES256Util.decryptAES256(env.getProperty(db+".username"), key));
			dataSource.setPassword(AES256Util.decryptAES256(env.getProperty(db+".password"), key));
			dataSource.setMaximumPoolSize(20);

		} catch (NullPointerException e) {
			log.error("[Default DataSource] DB Environment is NULL !!");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Default DataSource] DB ID/PW Decrypt or Connect Fail !!");
		}
        return dataSource;
    }

	public static DataSource createDataSource(String jdbcUrl, String driverClassName
			, String username, String password) {
		HikariDataSource dataSource = new HikariDataSource();
		try {

			dataSource.setJdbcUrl(jdbcUrl);
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUsername(AES256Util.decryptAES256(username, key));
			dataSource.setPassword(AES256Util.decryptAES256(password, key));
			dataSource.setMaximumPoolSize(20);

		} catch (NullPointerException e) {
			log.error("[Custom DataSource] DB Environment is NULL !!");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Custom DataSource] DB ID/PW Decrypt or Connect Fail !!");
		}
		return dataSource;
	}

	public static DataSource createCustomDataSource(RoutingDatabaseInfo routingDatabaseInfo) {
		HikariDataSource dataSource = new HikariDataSource();
		try {

			dataSource.setJdbcUrl(routingDatabaseInfo.getJdbcUrl());
			dataSource.setDriverClassName(routingDatabaseInfo.getDriverClassName());
			dataSource.setUsername(AES256Util.decryptAES256(routingDatabaseInfo.getUsername(), key));
			dataSource.setPassword(AES256Util.decryptAES256(routingDatabaseInfo.getPassword(), key));
			dataSource.setMaximumPoolSize(20);
			//dataSource.setReadOnly(true);

		} catch (NullPointerException e) {
			log.error("[Custom DataSource] DB Environment is NULL !!");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Custom DataSource] DB ID/PW Decrypt or Connect Fail !!");
		}
		return dataSource;
	}

}