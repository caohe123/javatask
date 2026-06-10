package com.library.common;

import com.library.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // 放行静态资源和登录注册页面
        if (uri.contains("login.jsp")
                || uri.contains("/login")
                || uri.contains("register.jsp")
                || uri.contains("/register")
                || uri.contains("/static/")
                || uri.contains(".css")
                || uri.contains(".js")) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) req.getSession().getAttribute("user");

        // 未登录
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 学生访问管理员页面
        if (uri.contains("/admin/") && !"admin".equals(user.getRole())) {
            resp.getWriter().write("权限不足");
            return;
        }

        chain.doFilter(request, response);
    }
}