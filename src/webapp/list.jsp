<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%-- 操作提示 --%>
<c:if test="${not empty msg || not empty param.msg}">
    <div class="msg">
            ${empty msg ? param.msg : msg}
    </div>
</c:if>

<%-- 搜索表单 --%>
<div class="search">
    <form action="${pageContext.request.contextPath}/book" method="get">
        <input type="hidden" name="action" value="list">
        <input type="text" name="keyword" value="${keyword}" placeholder="请输入书名搜索">
        <button type="submit">搜索</button>

        <%-- 修复1：管理员判断改为字符串比较 --%>
        <c:if test="${not empty user and user.role == 'admin'}">
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
                    <%-- 修复2：管理员修改删除按钮判断改为字符串 --%>
                    <c:if test="${not empty user and user.role == 'admin'}">
                        <a href="${pageContext.request.contextPath}/updata.jsp?id=${book.id}">修改信息</a>
                        <a href="${pageContext.request.contextPath}/book?action=delete&id=${book.id}" onclick="return confirm('确定删除该图书吗？删除后无法恢复')">删除图书</a>
                    </c:if>

                    <%-- 所有登录用户可见借阅按钮 --%>
                    <c:if test="${not empty user}">
                        <c:if test="${book.stock > 0}">
                            <a href="${pageContext.request.contextPath}/borrow?action=borrow&bookId=${book.id}">借阅图书</a>
                        </c:if>
                        <c:if test="${book.stock == 0}">
                            <span style="color:#999;">库存不足</span>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%-- 分页栏 --%>
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

<%-- 无数据提示 --%>
<c:if test="${empty pageBean or empty pageBean.list}">
    <div class="empty-tip">暂无匹配的图书数据</div>
</c:if>

<%-- 返回首页 --%>
<div class="back-home">
    <%-- 修复3：角色判断全部改为字符串 --%>
    <c:if test="${not empty user and user.role == 'admin'}">
        <a href="${pageContext.request.contextPath}/admin/index.jsp">返回管理员首页</a>
    </c:if>
    <c:if test="${not empty user and user.role == 'student'}">
        <a href="${pageContext.request.contextPath}/student/index.jsp">返回学生首页</a>
    </c:if>
    <c:if test="${empty user}">
        <a href="${pageContext.request.contextPath}/login.jsp">返回登录页</a>
    </c:if>
</div>
</body>
</html>