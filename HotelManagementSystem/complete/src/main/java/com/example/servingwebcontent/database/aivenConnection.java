package com.example.servingwebcontent.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class aivenConnection {
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try {
            Properties p = new Properties();
            try (InputStream in = aivenConnection.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in == null) throw new RuntimeException("Cannot find application.properties");
                p.load(in);
            }
            URL  = p.getProperty("spring.datasource.url");
            USER = p.getProperty("spring.datasource.username");
            PASS = p.getProperty("spring.datasource.password");

            Class.forName("com.mysql.cj.jdbc.Driver"); // đảm bảo driver đã load
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
