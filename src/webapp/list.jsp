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
        a{text-decoration: none; color: #0066cc; margin: 0 4px;}
        .msg{text-align: center; color: red; font-size: 16px; margin: 10px 0;}
        .back-home{text-align: center; margin-top: 10px;}
        .empty-tip{text-align: center; padding: 40px; color: #666; font-size: 15px;}
    </style>
</head>
<body>
<%-- 操作提示：兼容request域和参数传递的两种msg场景 --%>
<c:if test="${not empty msg || not empty param.msg}">
    <div class="msg">
            ${empty msg ? param.msg : msg}
    </div>
</c:if>

<%-- 搜索表单 --%>
<div class="search">
    <form action="${pageContext.request.contextPath}/book" method="get">
        <%-- 适配BaseServlet反射调用，固定指定list方法 --%>
        <input type="hidden" name="action" value="list">
        <input type="text" name="keyword" value="${keyword}" placeholder="请输入书名搜索">
        <button type="submit">搜索</button>

        <%-- 仅已登录的管理员显示新增按钮：先判断登录态，再判断角色，避免空指针报错 --%>
        <c:if test="${not empty loginUser and loginUser.role == 1}">
            <a href="${pageContext.request.contextPath}/add.jsp">【新增图书】</a>
        </c:if>
    </form>
</div>

<%-- 有数据时显示表格 --%>
<c:if test="${not empty pageBean and not empty pageBean.list}">
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
            <%-- 循环遍历图书列表 --%>
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
                        <%-- 仅管理员可见：修改、删除按钮 --%>
                    <c:if test="${not empty loginUser and loginUser.role == 1}">
                        <a href="${pageContext.request.contextPath}/update.jsp?id=${book.id}">修改</a>
                        <a href="${pageContext.request.contextPath}/book?action=delete&id=${book.id}" onclick="return confirm('确定删除该图书吗？')">删除</a>
                    </c:if>

                        <%-- 所有登录用户可见：借阅按钮 --%>
                    <c:if test="${book.stock > 0}">
                        <a href="${pageContext.request.contextPath}/borrow?action=borrow&bookId=${book.id}">借阅图书</a>
                    </c:if>
                    <c:if test="${book.stock == 0}">
                        <span style="color:#999;">库存不足</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%-- 分页栏：仅在有数据时显示 --%>
    <div class="page">
        <a href="${pageContext.request.contextPath}/book?action=list&currentPage=1&keyword=${keyword}">首页</a>

        <c:if test="${pageBean.currentPage > 1}">
            <a href="${pageContext.request.contextPath}/book?action=list&currentPage=${pageBean.currentPage - 1}&keyword=${keyword}">上一页</a>
        </c:if>

        第 ${pageBean.currentPage} 页 / 共 ${pageBean.totalPage} 页

        <c:if test="${pageBean.currentPage < pageBean.totalPage}">
            <a href="${pageContext.request.contextPath}/book?action=list&currentPage=${pageBean.currentPage + 1}&keyword=${keyword}">下一页</a>
        </c:if>

        <a href="${pageContext.request.contextPath}/book?action=list&currentPage=${pageBean.totalPage}&keyword=${keyword}">尾页</a>
    </div>
</c:if>

<%-- 无数据友好提示 --%>
<c:if test="${empty pageBean or empty pageBean.list}">
    <div class="empty-tip">暂无匹配的图书数据</div>
</c:if>

<%-- 返回首页：根据角色自动跳转对应首页 --%>
<div class="back-home">
    <c:if test="${not empty loginUser and loginUser.role == 1}">
        <a href="${pageContext.request.contextPath}/index.jsp">返回管理员首页</a>
    </c:if>
    <c:if test="${not empty loginUser and loginUser.role == 0}">
        <a href="${pageContext.request.contextPath}/studentIndex.jsp">返回学生首页</a>
    </c:if>
    <c:if test="${empty loginUser}">
        <a href="${pageContext.request.contextPath}/login.jsp">返回登录页</a>
    </c:if>
</div>
</body>
</html>