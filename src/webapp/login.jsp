<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
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
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }
        input, select {
            display: block;
            margin: 10px auto;
            padding: 10px;
            width: 200px;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>登录</h2>
    <form action="login" method="post">
        用户名: <input type="text" name="username" required>
        密码: <input type="password" name="password" required>
        <input type="submit" value="登录">
    </form>
</div>
</body>
</html>