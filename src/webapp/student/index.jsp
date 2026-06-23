<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生首页</title>
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
        .welcome{
            color: #333;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="box">
    <h2 class="welcome">欢迎 ${loginUser.username} 登录图书管理系统</h2>

    <%-- 学生功能入口 --%>
    <a href="${pageContext.request.contextPath}/book">查看图书列表</a>
    <a href="${pageContext.request.contextPath}/borrow?action=list">我的借阅记录</a>

    <%-- 退出登录 --%>
    <a href="${pageContext.request.contextPath}/login.jsp">退出登录</a>
</div>
</body>
</html>