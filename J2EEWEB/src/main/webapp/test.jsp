<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/31
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>Title</title>
</head>

<body>

<%--测试: map 传递的数据如何获取--%>
<a href="${pageContext.request.contextPath}/DemoServlet">点击跳转</a><br/>
<c:forEach items="${map}" var="m">
    ${m.key}
    <%--${m.name}<br/>--%>
    ${m.value[0]}<br/>
</c:forEach>
${map.name[0]}
${map["name"][0]}
${map.get("name")[0]}

</body>

</html>
