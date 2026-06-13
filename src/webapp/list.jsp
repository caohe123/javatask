<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 引入JSTL核心标签库，用于循环、判断 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>图书列表</title>
    <style>
        table{border-collapse: collapse; width: 80%; margin: 20px auto;}
        th,td{border: 1px solid #ccc; padding: 8px; text-align: center;}
        th{background-color: #f5f5f5;}
        .search{text-align: center; margin: 20px 0;}
        .page{text-align: center; margin: 20px 0;}
        a{text-decoration: none; color: #0066cc;}
    </style>
</head>
<body>
    <%-- 操作提示：接收Servlet传来的msg，展示成功/失败信息 --%>
    <c:if test="${not empty param.msg}">
        <div style="text-align: center; color: red; font-size: 16px;">
            ${param.msg}
        </div>
    </c:if>

    <%-- 搜索表单：提交到 /book 走BookServlet的doGet --%>
    <div class="search">
        <form action="book" method="get">
            <input type="text" name="keyword" value="${keyword}" placeholder="请输入书名搜索">
            <button type="submit">搜索</button>
            <a href="add.jsp">【新增图书】</a>
        </form>
    </div>

    <%-- 图书数据表格 --%>
    <table>
        <tr>
            <th>图书ID</th>
            <th>书名</th>
            <th>作者</th>
            <th>出版社</th>
            <th>价格</th>
            <th>库存</th>
            <th>分类</th>
            <th>操作</th>
        </tr>
        <%-- 循环遍历 PageBean 中的图书列表 --%>
        <c:forEach items="${pageBean.list}" var="book">
            <tr>
                <td>${book.id}</td>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.publisher}</td>
                <td>${book.price}</td>
                <td>${book.stock}</td>
                <td>${book.category}</td>
                <td>
                    <%-- 修改：跳转到修改页，并携带图书ID --%>
                    <a href="update.jsp?id=${book.id}">修改</a>
                    <%-- 删除：提交POST请求，防止URL篡改 --%>
                    <a href="book?action=delete&id=${book.id}" onclick="return confirm('确定删除吗？')">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%-- 分页栏 --%>
    <div class="page">
        <%-- 首页 --%>
        <a href="book?currentPage=1&keyword=${keyword}">首页</a>

        <%-- 上一页：当前页>1 才显示 --%>
        <c:if test="${pageBean.currentPage > 1}">
            <a href="book?currentPage=${pageBean.currentPage - 1}&keyword=${keyword}">上一页</a>
        </c:if>

        <%-- 当前页码展示 --%>
        第 ${pageBean.currentPage} 页 / 共 ${pageBean.totalPage} 页

        <%-- 下一页：当前页 < 总页数 才显示 --%>
        <c:if test="${pageBean.currentPage < pageBean.totalPage}">
            <a href="book?currentPage=${pageBean.currentPage + 1}&keyword=${keyword}">下一页</a>
        </c:if>

        <%-- 尾页 --%>
        <a href="book?currentPage=${pageBean.totalPage}&keyword=${keyword}">尾页</a>
    </div>
</body>
</html>