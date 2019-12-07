<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/3
  Time: 23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>accept information</title>
</head>
<body>
    <%
        if(request.getParameter("user") != null){
    %>

    <div>
        <h5>login success!<%= request.getParameter("role") %></h5>
    </div>

    <% 	} %>
</body>
</html>
