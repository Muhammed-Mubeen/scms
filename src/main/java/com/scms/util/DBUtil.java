package com.scms.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.util.Properties;

public class DBUtil {

    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream input = DBUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}