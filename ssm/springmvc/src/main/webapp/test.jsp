<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/4/19 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试 spring 的请求方式设置</title>
</head>
<body>

<a href="user/hello0">get请求</a> <%--设置了 method = RequestMethod.POST, 只支持post--%>
<form action="user/hello0" method="post"> <%--设置了 params={"username", "password"}, 请求参数必须要有 username,password--%>
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <input type="submit" value="post"/>
</form>

</body>
</html>
