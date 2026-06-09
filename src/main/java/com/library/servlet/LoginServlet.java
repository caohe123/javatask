package com.library.servlet;

import com.library.model.User;
import com.library.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 这里必须删掉 location:
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. 获取表单参数（这里也不能写 name:）
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 3. 处理 null 情况
        if (username == null) username = "";
        if (password == null) password = "";

        // 4. 去空格
        username = username.trim();
        password = password.trim();

        // 5. 调用登录逻辑
        User user = userService.login(username, password);

        if (user != null) {
            // 登录成功，保存用户到 session
            request.getSession().setAttribute("user", user);

            // 根据 role 跳转
            if ("admin".equals(user.getRole())) {
                response.sendRedirect("admin/index.jsp");
            } else if ("student".equals(user.getRole())) {
                response.sendRedirect("student/index.jsp");
            } else {
                // 如果 role 不明，默认跳回登录页
                response.sendRedirect("login.jsp?error=2");
            }
        } else {
            // 登录失败
            response.sendRedirect("login.jsp?error=1");
        }
    }
}