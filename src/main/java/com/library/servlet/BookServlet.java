package com.library.servlet;

import com.library.model.Book;
import com.library.model.PageBean;
import com.library.model.User;
import com.library.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    // 加final消除警告，该对象无需重新赋值
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listBooks(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/book?action=list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                addBook(request, response);
                break;
            case "update":
                updateBook(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/book?action=list");
                break;
        }
    }

    // 管理员权限校验：修复字符串和int比较的报错
    private boolean checkAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loginUser = (User) request.getSession().getAttribute("user");
        // role是字符串，用equals比较，和你的LoginServlet保持一致
        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=无操作权限");
            return false;
        }
        return true;
    }

    // 图书列表+分页+搜索
    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String keyword = request.getParameter("keyword");

        int currentPage = 1;
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (keyword == null) {
            keyword = "";
        }

        PageBean<Book> pageBean = bookService.findBookByPage(currentPage, keyword);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    // 新增图书
    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!checkAdmin(request, response)) return;

        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String category = request.getParameter("category");

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setStock(stock);
        book.setCategory(category);
        boolean flag = bookService.addBook(book);

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/book?action=list&msg=新增成功");
        } else {
            response.sendRedirect(request.getContextPath() + "/add.jsp?msg=新增图书失败，请重试");
        }
    }

    // 删除图书
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!checkAdmin(request, response)) return;

        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = bookService.deleteBook(id);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/book?action=list&msg=删除成功");
        } else {
            response.sendRedirect(request.getContextPath() + "/book?action=list&msg=删除失败");
        }
    }

    // 修改图书
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!checkAdmin(request, response)) return;

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
            response.sendRedirect(request.getContextPath() + "/book?action=list&msg=修改成功");
        } else {
            response.sendRedirect(request.getContextPath() + "/updata.jsp?id=" + id + "&msg=修改失败");
        }
    }
}