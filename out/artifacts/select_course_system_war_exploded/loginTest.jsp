<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/3
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>这是标题</title>
</head>
<body>
<div class="container margin-top-80">
    <%--表单提交demo--%>
    <form action="./login" method="post" class="max-width-330 layout-center">
        <h2>Please sign in</h2>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required>

        <label for="inputPassword1" class="sr-only">Password</label>
        <input type="password" id="inputPassword1" name="password" class="form-control" placeholder="Password" required>

        <select name="identity">
            <option value ="root">管理员</option>
            <option value ="instructor">教师</option>
            <option value="student">学生</option>
        </select>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <a href="./register.jsp">Sign up</a>
    </form>
</div>
</body>
</html>
