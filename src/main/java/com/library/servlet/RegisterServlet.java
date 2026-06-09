package com.library.servlet;

import com.library.model.User;
import com.library.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 直接跳转注册页
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String realname = request.getParameter("realname");
        String role = request.getParameter("role"); // student/admin

        // 处理空值
        if(username == null) username = "";
        if(password == null) password = "";
        if(realname == null) realname = "";
        if(role == null) role = "student";

        username = username.trim();
        password = password.trim();
        realname = realname.trim();
        role = role.trim();

        // 封装 User 对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealname(realname);
        user.setRole(role);

        // 调用 Service 注册
        boolean success = userService.register(user);

        if(success){
            // 注册成功，跳转登录页
            response.sendRedirect("login.jsp");
        } else {
            // 注册失败（用户名已存在），带错误信息回注册页
            request.setAttribute("msg", "注册失败：用户名已存在");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}