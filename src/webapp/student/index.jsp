<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生首页</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            text-align: center;
            padding-top: 100px;
        }
        .container {
            display: inline-block;
            padding: 40px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }
        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 15px 30px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        a.button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>学生首页</h1>
    <p>欢迎：${sessionScope.user.username} (${sessionScope.user.realname})</p>
    <a class="button" href="${pageContext.request.contextPath}/logout">退出登录</a>
</div>
</body>
</html>