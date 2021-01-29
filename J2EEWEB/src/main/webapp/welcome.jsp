<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/29
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
</head>

<body>

<h2>welcome!&nbsp;${sessionScope.admin.username}</h2>
<hr/>

<h1>${admin.username}&nbsp;welcome</h1>
<a href="${pageContext.request.contextPath}/pages?pageNumber=1">查看所有联系人信息</a>
<a href="${pageContext.request.contextPath}/retrieve">retrieve all contacts</a> <%--查看所有联系人: 跳转 RetrieveServlet--%>
<br/>
<a href="${pageContext.request.contextPath}/create.jsp">create a contact</a> <%--新建联系人: 跳转 新页面--> create.jsp--%>
<br/>
<a href="${pageContext.request.contextPath}/pages?pageNumber=1">pages</a>

</body>

</html>
