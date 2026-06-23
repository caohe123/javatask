<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的借阅记录</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: "Microsoft YaHei", sans-serif;
            background-color: #f8f9fa;
            padding: 30px 0;
        }
        .container {
            width: 1000px;
            margin: 0 auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
            padding: 30px;
        }
        .title {
            font-size: 22px;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e9ecef;
        }
        .msg {
            padding: 10px 15px;
            margin-bottom: 18px;
            border-radius: 4px;
            font-size: 14px;
        }
        .msg-error {
            color: #721c24;
            background-color: #f8d7da;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
        }
        table th, table td {
            border: 1px solid #dee2e6;
            padding: 12px;
            text-align: center;
            font-size: 14px;
        }
        table th {
            background-color: #f1f3f5;
            font-weight: 600;
            color: #495057;
        }
        table tr:hover {
            background-color: #f8f9fa;
        }
        .status-normal {
            color: #1971c2;
            font-weight: 500;
        }
        .status-returned {
            color: #2f9e44;
            font-weight: 500;
        }
        .status-overdue {
            color: #e03131;
            font-weight: bold;
        }
        .btn {
            display: inline-block;
            padding: 6px 14px;
            text-decoration: none;
            color: #fff;
            border-radius: 4px;
            font-size: 13px;
            border: none;
            cursor: pointer;
        }
        .btn-return {
            background-color: #e03131;
        }
        .btn-return:hover {
            background-color: #c92a2a;
        }
        .btn-disabled {
            background-color: #adb5bd;
            cursor: not-allowed;
        }
        .pagination {
            text-align: center;
            margin-bottom: 10px;
        }
        .pagination a {
            display: inline-block;
            padding: 7px 12px;
            margin: 0 3px;
            border: 1px solid #dee2e6;
            text-decoration: none;
            color: #1971c2;
            border-radius: 4px;
            font-size: 13px;
        }
        .pagination a.active {
            background-color: #1971c2;
            color: #fff;
            border-color: #1971c2;
        }
        .pagination a:hover:not(.active) {
            background-color: #e7f5ff;
        }
        .empty {
            text-align: center;
            padding: 60px 0;
            color: #868e96;
            font-size: 15px;
        }
        .back-bar {
            text-align: center;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #e9ecef;
        }
        .back-bar a {
            color: #1971c2;
            text-decoration: none;
            font-size: 14px;
        }
        .back-bar a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title">我的借阅记录</div>

    <%-- 操作提示 --%>
    <c:if test="${not empty msg}">
        <div class="msg msg-error">${msg}</div>
    </c:if>

    <%-- 借阅记录表格 --%>
    <c:if test="${not empty borrowPage.list and borrowPage.totalCount > 0}">
        <table>
            <thead>
            <tr>
                <th>借阅ID</th>
                <th>图书ID</th>
                <th>借阅时间</th>
                <th>应还时间</th>
                <th>归还时间</th>
                <th>借阅状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${borrowPage.list}" var="borrow">
                <tr>
                    <td>${borrow.id}</td>
                    <td>${borrow.bookId}</td>
                    <td>${borrow.borrowTime}</td>
                    <td>${borrow.dueTime}</td>
                    <td>
                        <c:if test="${empty borrow.returnTime}">-</c:if>
                        <c:if test="${not empty borrow.returnTime}">${borrow.returnTime}</c:if>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${borrow.status == 0}">
                                <span class="status-normal">未归还</span>
                            </c:when>
                            <c:when test="${borrow.status == 1}">
                                <span class="status-returned">已归还</span>
                            </c:when>
                            <c:when test="${borrow.status == 2}">
                                <span class="status-overdue">逾期未还</span>
                            </c:when>
                            <c:otherwise>
                                <span>未知状态</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                            <%-- 仅未归还/逾期状态显示还书按钮 --%>
                        <c:if test="${borrow.status == 0 or borrow.status == 2}">
                            <a href="${pageContext.request.contextPath}/borrow?action=returnBook&borrowId=${borrow.id}"
                               class="btn btn-return"
                               onclick="return confirm('确认归还该图书吗？')">
                                归还图书
                            </a>
                        </c:if>
                        <c:if test="${borrow.status == 1}">
                            <span class="btn btn-disabled">已完成</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- 分页控件 --%>
        <div class="pagination">
                <%-- 首页 --%>
            <c:if test="${borrowPage.currentPage > 1}">
                <a href="${pageContext.request.contextPath}/borrow?action=list&pageNum=1">首页</a>
            </c:if>

                <%-- 上一页 --%>
            <c:if test="${borrowPage.currentPage > 1}">
                <a href="${pageContext.request.contextPath}/borrow?action=list&pageNum=${borrowPage.currentPage - 1}">上一页</a>
            </c:if>

                <%-- 页码 --%>
            <c:forEach begin="1" end="${borrowPage.totalPage}" var="i">
                <a href="${pageContext.request.contextPath}/borrow?action=list&pageNum=${i}"
                   class="${borrowPage.currentPage == i ? 'active' : ''}">${i}</a>
            </c:forEach>

                <%-- 下一页 --%>
            <c:if test="${borrowPage.currentPage < borrowPage.totalPage}">
                <a href="${pageContext.request.contextPath}/borrow?action=list&pageNum=${borrowPage.currentPage + 1}">下一页</a>
            </c:if>

                <%-- 尾页 --%>
            <c:if test="${borrowPage.currentPage < borrowPage.totalPage}">
                <a href="${pageContext.request.contextPath}/borrow?action=list&pageNum=${borrowPage.totalPage}">尾页</a>
            </c:if>
        </div>
    </c:if>

    <%-- 无数据提示 --%>
    <c:if test="${empty borrowPage.list or borrowPage.totalCount == 0}">
        <div class="empty">暂无借阅记录，快去图书列表借阅图书吧~</div>
    </c:if>

    <%-- 返回按钮：按角色适配首页 --%>
    <div class="back-bar">
        <c:if test="${loginUser.role == 1}">
            <a href="${pageContext.request.contextPath}/index.jsp">← 返回管理员首页</a>
        </c:if>
        <c:if test="${loginUser.role == 0}">
            <a href="${pageContext.request.contextPath}/studentIndex.jsp">← 返回学生首页</a>
        </c:if>
    </div>
</div>
</body>
</html>