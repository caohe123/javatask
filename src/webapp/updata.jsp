<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.library.model.Book" %>
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
    <%-- 先根据URL里的id查询图书信息，回显到表单里 --%>
    <%
        // 1. 获取要修改的图书ID
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            // 如果没有id，直接跳回列表页
            response.sendRedirect("book?msg=参数错误");
            return;
        }
        int id = Integer.parseInt(idStr);

        // 2. 调用BookService查询图书详情
        BookService bookService = new BookService();
        Book book = bookService.getBookById(id);

        // 3. 如果图书不存在，跳回列表页
        if (book == null) {
            response.sendRedirect("book?msg=图书不存在");
            return;
        }

        // 4. 把图书对象存入request域，页面用EL表达式回显
        request.setAttribute("book", book);
    %>

    <%-- 操作提示信息（修改失败的提示） --%>
    <div style="text-align: center; color: red;">
        ${param.msg}
    </div>

    <%-- 表单提交到BookServlet，POST方式，标识为update操作 --%>
    <form action="book" method="post">
        <%-- 隐藏域：标识操作类型为update，同时传递图书ID --%>
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
            <a href="book">返回列表</a>
        </div>
    </form>
</body>
</html>

