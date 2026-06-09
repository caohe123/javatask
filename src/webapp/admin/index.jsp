<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员首页</title>
</head>
<body>
<h1>管理员首页</h1>
欢迎：${sessionScope.user.username} (${sessionScope.user.realname})
<br>
<a href="../LogoutServlet">退出登录</a>
</body>
</html>