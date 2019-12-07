<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/3
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>accept error</title>
</head>
<body>
    <%
    if(request.getAttribute("error") != null){
    %>

    <div>
        <h5>login fail!<%= request.getAttribute("error") %></h5>
    </div>

    <% 	} %>
</body>
</html>
