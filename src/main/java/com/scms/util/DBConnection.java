package com.scms.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();

            // Load from environment variables (Azure) or fallback to local
            String host     = getEnv("DB_HOST",     "localhost");
            String dbName   = getEnv("DB_NAME",     "scms_db");
            String user     = getEnv("DB_USER",     "root");
            String password = getEnv("DB_PASS",     "yourpassword");
            String port     = getEnv("DB_PORT",     "3306");

            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + dbName +
                    "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setPoolName("SCMS-Pool");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ DB Connection Pool initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to initialize DB connection pool.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static String getEnv(String key, String fallback) {
        String val = System.getenv(key);
        return (val != null && !val.isBlank()) ? val : fallback;
    }

    // Call this on app shutdown (in ServletContextListener)
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("🔒 DB Connection Pool shut down.");
        }
    }
}