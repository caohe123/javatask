<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.library.model.Book" %>
<%@ page import="com.library.model.User" %>
<%@ page import="com.library.service.BookService" %>
<html>
<head>
    <title>修改图书</title>
    <style>
        form{width: 400px; margin: 50px auto;}
        div{margin: 15px 0;}
        label{display: inline-block; width: 80px;}
        input{padding: 4px; width: 220px;}
        button{margin-left: 80px; padding: 5px 20px;}
    </style>
</head>
<body>
    <%
        // 1. 管理员权限校验：仅管理员可进入修改页
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/book?msg=无操作权限");
            return;
        }

        // 2. 获取URL传递的图书ID，空值拦截
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/book?msg=参数错误");
            return;
        }
        int id = Integer.parseInt(idStr);

        // 3. 查询图书详情，用于表单回显
        BookService bookService = new BookService();
        Book book = bookService.getBookById(id);

        // 4. 图书不存在时跳转回列表
        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/book?msg=图书不存在");
            return;
        }
        request.setAttribute("book", book);
    %>

    <%-- 操作结果提示 --%>
    <div style="text-align: center; color: red;">
        ${param.msg}
    </div>

    <%-- 提交修改表单，POST方式提交到BookServlet --%>
    <form action="${pageContext.request.contextPath}/book" method="post">
        <%-- 隐藏域：标识操作类型 + 传递图书ID --%>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${book.id}">

        <div>
            <label>书名：</label>
            <input type="text" name="name" value="${book.name}" required>
        </div>
        <div>
            <label>作者：</label>
            <input type="text" name="author" value="${book.author}" required>
        </div>
        <div>
            <label>出版社：</label>
            <input type="text" name="publisher" value="${book.publisher}" required>
        </div>
        <div>
            <label>价格：</label>
            <input type="number" step="0.01" name="price" value="${book.price}" required>
        </div>
        <div>
            <label>库存：</label>
            <input type="number" name="stock" value="${book.stock}" required>
        </div>
        <div>
            <label>分类：</label>
            <input type="text" name="category" value="${book.category}" required>
        </div>

        <div>
            <button type="submit">提交修改</button>
            <a href="${pageContext.request.contextPath}/book">返回列表</a>
        </div>
    </form>
</body>
</html>