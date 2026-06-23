package com.library.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    // 改成你自己的库名、账号、密码
    private static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PWD = ".Zzf070408";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ 驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 驱动加载失败");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("✅ 数据库连接成功");
        } catch (SQLException e) {
            System.out.println("❌ 数据库连接失败，URL/账号/密码错误？");
            System.out.println("错误信息：" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    // 关键：三参 close，和 UserDao 调用匹配
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}