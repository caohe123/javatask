<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图书馆管理系统 - 登录</title>
</head>
<body>
<h1>图书馆管理系统 - 登录</h1>
<%-- 只有error=1时才显示错误提示 --%>
<% if ("1".equals(request.getParameter("error"))) { %>
    <p style="color:red">用户名或密码错误</p>
<% } %>
<form action="login" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit" value="登录">
</form>
</body>
</html>