<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/3
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>test</title>
  </head>
  <body>
  lalala
  <form action="searchCourseServlet" method="get">
    <div class="uk-width-1-3@s">
      <input class="uk-input" type="number" placeholder="课程代码">
    </div>
    <div class="uk-width-1-6@s">
      <input class="uk-input" type="number" placeholder="课程序号">
    </div>
    <div class="uk-width-1-3@s">
      <input class="uk-input" type="text" placeholder="课程名称">
    </div>

    <div class="uk-width-1-6@s">
      <button type="submit">查询</button>
    </div>
  </form>
  </body>
</html>
