package com.library.dao;

import com.library.common.DBUtil;
import com.library.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User login(String username, String password) {
        System.out.println("================ UserDao 开始 ================");
        System.out.println("原始输入 username: [" + username + "]");
        System.out.println("原始输入 password: [" + password + "]");

        // ✅ 关键修复：先判断 null，再 trim
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        password = password.trim();
        System.out.println("去空格后 username: [" + username + "]");
        System.out.println("去空格后 password: [" + password + "]");

        Connection conn = DBUtil.getConnection();
        if (conn == null) {
            System.out.println("❌ DBUtil.getConnection() 返回 null → 数据库连不上");
            return null;
        }
        System.out.println("✅ 数据库连接成功");

        // 先查整张表，确认当前库有没有 admin 用户
        try {
            PreparedStatement pstmtCheck = conn.prepareStatement("SELECT * FROM `user`");
            ResultSet rsCheck = pstmtCheck.executeQuery();
            System.out.println("==== 当前库 user 表所有数据 ====");
            while (rsCheck.next()) {
                System.out.println(
                        "id=" + rsCheck.getInt("id") +
                                ", username=[" + rsCheck.getString("username") + "]" +
                                ", password=[" + rsCheck.getString("password") + "]"
                );
            }
            rsCheck.close();
            pstmtCheck.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "SELECT * FROM `user` WHERE username=? AND password=?";
            System.out.println("执行 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            System.out.println("参数 1: [" + username + "]");
            System.out.println("参数 2: [" + password + "]");

            rs = pstmt.executeQuery();
            System.out.println("执行查询完毕，准备判断结果集");

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                System.out.println("✅ 查询到用户: " + user.getUsername());
            } else {
                System.out.println("❌ rs.next() 返回 false → 没查到任何数据");
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL 异常:");
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        System.out.println("================ UserDao 结束，返回 user = " + user);
        return user;
    }
}