<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增图书</title>
    <style>
        form{width: 400px; margin: 50px auto;}
        div{margin: 15px 0;}
        label{display: inline-block; width: 80px;}
        input{padding: 4px; width: 220px;}
        .btn-group{margin-left: 80px; display: flex; gap: 10px; align-items: center;}
        button, .back-btn{
            padding: 5px 20px;
            cursor: pointer;
            border: 1px solid #0066cc;
            border-radius: 3px;
            text-decoration: none;
            font-size: 14px;
            line-height: 1.5;
        }
        button{
            background-color: #0066cc;
            color: white;
        }
        .back-btn{
            background-color: white;
            color: #0066cc;
            text-align: center;
            box-sizing: border-box;
        }
        .back-btn:hover{
            background-color: #f0f5ff;
        }
    </style>
</head>
<body>
<div style="text-align: center; color: red;">
    ${msg}
</div>

<%-- 表单提交到 BookServlet，POST方式提交新增 --%>
<form action="${pageContext.request.contextPath}/book" method="post">
    <%-- 标识操作类型：add 新增 --%>
    <input type="hidden" name="action" value="add">

    <div>
        <label>书名：</label>
        <input type="text" name="name" required>
    </div>
    <div>
        <label>作者：</label>
        <input type="text" name="author" required>
    </div>
    <div>
        <label>出版社：</label>
        <input type="text" name="publisher" required>
    </div>
    <div>
        <label>价格：</label>
        <input type="number" step="0.01" name="price" required>
    </div>
    <div>
        <label>库存：</label>
        <input type="number" name="stock" required>
    </div>
    <div>
        <label>分类：</label>
        <input type="text" name="category" required>
    </div>

    <div class="btn-group">
        <button type="submit">提交新增</button>
        <%-- 返回管理员首页按钮：路径已修改为 admin/index.jsp --%>
        <a href="${pageContext.request.contextPath}/admin/index.jsp" class="back-btn">返回管理员首页</a>
    </div>
</form>
</body>
</html>