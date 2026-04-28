package com.scms.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBUtil {

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();

            String host     = getEnv("DB_HOST",     "localhost");
            String port     = getEnv("DB_PORT",     "3306");
            String dbName   = getEnv("DB_NAME",     "scms");       // local = scms
            String user     = getEnv("DB_USER",     "root");
            String password = getEnv("DB_PASSWORD", "mypassword");

            // Azure needs useSSL=true, local needs false
            String ssl = getEnv("DB_HOST", "localhost").equals("localhost") ? "false" : "true";

            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + dbName +
                    "?useSSL=" + ssl + "&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setPoolName("SCMS-Pool");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ DB Pool initialized: " + host + "/" + dbName);

        } catch (Exception e) {
            System.err.println("❌ DB Pool failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) throw new RuntimeException("DataSource is null — check DB env vars");
        return dataSource;
    }

    private static String getEnv(String key, String fallback) {
        String val = System.getenv(key);
        return (val != null && !val.isBlank()) ? val : fallback;
    }
}