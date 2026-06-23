<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>逾期图书统计</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: "Microsoft YaHei", sans-serif;
            padding: 20px;
        }
        .container {
            width: 1200px;
            margin: 0 auto;
        }
        .title {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        table th {
            background-color: #f5f5f5;
            font-weight: bold;
        }
        .overdue {
            color: red;
            font-weight: bold;
        }
        .empty {
            text-align: center;
            padding: 50px;
            color: #666;
            font-size: 16px;
        }
        .count {
            font-size: 18px;
            margin-bottom: 15px;
            color: #d8000c;
            font-weight: bold;
        }
        .back {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title">逾期图书统计</div>

    <%-- 逾期总数提示 --%>
    <div class="count">当前逾期图书总数：${fn:length(overdueList)} 本</div>

    <%-- 逾期列表展示 --%>
    <c:if test="${not empty overdueList}">
        <table>
            <thead>
            <tr>
                <th>借阅ID</th>
                <th>用户ID</th>
                <th>图书ID</th>
                <th>借阅时间</th>
                <th>应还时间</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${overdueList}" var="borrow">
                <tr>
                    <td>${borrow.id}</td>
                    <td>${borrow.userId}</td>
                    <td>${borrow.bookId}</td>
                    <td>${borrow.borrowTime}</td>
                    <td class="overdue">${borrow.dueTime}</td>
                    <td class="overdue">逾期未还</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <%-- 无逾期记录提示 --%>
    <c:if test="${empty overdueList}">
        <div class="empty">暂无逾期图书记录</div>
    </c:if>

    <div class="back">
        <a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
    </div>
</div>
</body>
</html>