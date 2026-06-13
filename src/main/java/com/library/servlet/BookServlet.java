package com.library.servlet;

import com.library.model.Book;
import com.library.model.PageBean;
import com.library.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 和LoginServlet一样，用@WebServlet注解配置访问路径
@WebServlet("/book")
public class BookServlet extends HttpServlet {
    // 和LoginServlet一样，实例化业务层对象
    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 和LoginServlet一样，先设置编码，防止中文乱码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. 获取前端传来的参数（和LoginServlet获取username/password的方式一致）
        String currentPageStr = request.getParameter("currentPage");
        String keyword = request.getParameter("keyword");

        // 3. 处理参数默认值（和LoginServlet处理null的方式一致）
        int currentPage = 1;
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (keyword == null) {
            keyword = "";
        }

        // 4. 调用BookService的分页查询方法（和LoginServlet调用userService.login的方式一致）
        PageBean<Book> pageBean = bookService.findBookByPage(currentPage, keyword);

        // 5. 把数据存入request域，传给前端页面
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("keyword", keyword);

        // 6. 转发到图书列表页面（和LoginServlet的sendRedirect逻辑对应，这里用转发）
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 和LoginServlet一样，先设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. 获取操作类型action（和LoginServlet获取参数的方式一致）
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addBook(request, response);
        } else if ("delete".equals(action)) {
            deleteBook(request, response);
        } else if ("update".equals(action)) {
            updateBook(request, response);
        } else {
            // 没有匹配操作，默认跳回列表页（和LoginServlet的跳转逻辑一致）
            response.sendRedirect("book");
        }
    }

    // 新增图书方法，结构和LoginServlet的doPost处理逻辑对齐
    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 获取表单参数
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String category = request.getParameter("category");

        // 2. 封装Book对象
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setStock(stock);
        book.setCategory(category);

        // 3. 调用Service新增（和LoginServlet调用userService.login的方式一致）
        boolean success = bookService.addBook(book);

        // 4. 跳转逻辑，和LoginServlet的登录成功/失败跳转一致
        if (success) {
            response.sendRedirect("book?msg=新增成功");
        } else {
            response.sendRedirect("add.jsp?msg=新增失败");
        }
    }

    // 删除图书方法
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = bookService.deleteBook(id);

        if (success) {
            response.sendRedirect("book?msg=删除成功");
        } else {
            response.sendRedirect("book?msg=删除失败");
        }
    }

    // 修改图书方法
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String category = request.getParameter("category");

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setStock(stock);
        book.setCategory(category);

        boolean success = bookService.updateBook(book);

        if (success) {
            response.sendRedirect("book?msg=修改成功");
        } else {
            response.sendRedirect("update.jsp?id=" + id + "&msg=修改失败");
        }
    }
}