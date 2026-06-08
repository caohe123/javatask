package com.library.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected void forward(String path, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    protected void redirect(String path, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(path);
    }
}