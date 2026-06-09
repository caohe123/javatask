<form action="register" method="post">
    用户名: <input type="text" name="username" required><br>
    密码: <input type="password" name="password" required><br>
    真实姓名: <input type="text" name="realname" required><br>
    角色:
    <select name="role">
        <option value="student">学生</option>
        <option value="admin">管理员</option>
    </select><br>
    <input type="submit" value="注册">
</form>