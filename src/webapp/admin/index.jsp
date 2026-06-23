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
        <%-- 访问BookServlet，跳转图书列表页 --%>
        <a href="${pageContext.request.contextPath}/book">图书列表管理</a>
        <%-- 绝对根路径访问根目录add.jsp，杜绝404 --%>
        <a href="${pageContext.request.contextPath}/add.jsp">新增图书</a>
        <%-- 单独点修改页会缺id，弹窗提示，不直接跳转 --%>
        <a href="#" onclick="alert('请先在图书列表选择图书再修改！')">修改图书</a>
        <%-- 返回登录页 --%>
        <a href="${pageContext.request.contextPath}/login.jsp">退出登录</a>
    </div>
</body>
</html>