<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/30
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/demo" method="get">
    <input type="checkbox" name="female"> female
    <input type="checkbox" name="male"> male
    <input type="submit" value="submit">
</form>
</body>
</html>
