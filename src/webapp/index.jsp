<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理系统</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: url("static/images/library_bg.jpg");
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            background-attachment: fixed;
        }
        .container {
            width: 400px;
            margin: 200px auto;
            padding: 40px;
            background: rgba(255,255,255,0.85);
            border-radius: 12px;
            text-align: center;
        }
        a.button {
            display: block;
            margin: 20px auto;
            padding: 15px 30px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 18px;
        }
        a.button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>欢迎来到图书管理系统</h1>
    <a class="button" href="login.jsp">登录</a>
    <a class="button" href="register.jsp">注册</a>
</div>
</body>
</html>