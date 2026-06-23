package com.library.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    // ========== 修复405核心：新增doGet方法，统一转发给doPost ==========
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET请求直接复用POST的处理逻辑，无需重复写反射代码
        doPost(req, resp);
    }
    // ==============================================================

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 接收action参数，确定要调用的方法名
        String action = req.getParameter("action");
        if (action == null || action.trim().length() == 0) {
            action = "list"; // 新增：默认走列表方法
        }
        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        try {
            // 2. 反射获取子类中对应的业务方法（固定参数：HttpServletRequest + HttpServletResponse）
            Method method = this.getClass().getMethod(
                    action,
                    HttpServletRequest.class,
                    HttpServletResponse.class
            );

            // 3. 执行方法，接收返回的跳转路径
            String result = (String) method.invoke(this, req, resp);

            // 4. 统一处理页面跳转
            if (result != null && !result.isEmpty()) {
                if (result.startsWith("redirect:")) {
                    // 重定向：浏览器发起新请求
                    String path = result.substring("redirect:".length());
                    resp.sendRedirect(path);
                } else {
                    // 服务器转发：地址栏不变，共享request域数据
                    req.getRequestDispatcher(result).forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("调用业务方法失败：" + action, e);
        }
    }
}