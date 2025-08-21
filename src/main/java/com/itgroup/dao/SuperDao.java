package com.itgroup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SuperDao {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "papa";
    private static final String PASS = "hello12345";

    static {
        try { Class.forName(DRIVER); }
        catch (Exception e) { throw new RuntimeException("Oracle JDBC Driver 로드 실패", e); }
    }

    protected Connection getConnection() throws Exception {
        // 간단히 auto-commit 사용
        return DriverManager.getConnection(URL, USER, PASS);
    }

}