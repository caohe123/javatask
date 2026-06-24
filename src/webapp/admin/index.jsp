<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员首页</title>
    <style>
        .box{
            width: 500px;
            margin: 100px auto;
            text-align: center;
        }
        a{
            display: inline-block;
            margin: 15px 10px;
            padding: 10px 25px;
            border: 1px solid #0066cc;
            text-decoration: none;
            color: #0066cc;
            border-radius: 4px;
        }
        a:hover{
            background-color: #0066cc;
            color: white;
        }
    </style>
</head>
<body>
<div class="box">
    <h2>欢迎管理员登录图书管理系统</h2>

    <%-- 图书管理相关按钮 --%>
    <a href="${pageContext.request.contextPath}/book">图书列表管理</a>
    <a href="${pageContext.request.contextPath}/add.jsp">新增图书</a>
    <a href="#" onclick="alert('请先在图书列表选择图书再修改！')">修改图书</a>

    <%-- 借阅管理入口：修改为管理员全量借阅记录 --%>
    <a href="${pageContext.request.contextPath}/borrow?action=allList">全部借阅记录</a>
    <a href="${pageContext.request.contextPath}/borrow?action=overdue">逾期图书管理</a>

    <%-- 退出登录 --%>
    <a href="${pageContext.request.contextPath}/login.jsp">退出登录</a>
</div>
</body>
</html>