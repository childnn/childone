<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/28
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>联系人</title>
</head>
<body>

<table border="1xp solid black">
    <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>性别</td>
        <td>年龄</td>
        <td>籍贯</td>
        <td>QQ</td>
        <td>邮箱</td>
        <td>操作</td>
    </tr>

    <c:if test="${not empty list}"><%--非空校验--%>
        <c:forEach items="${list}" var="contact" varStatus="vs">
            <%--遍历显示 list 中的 对象--%>
            <tr>
                <td>${vs.count}</td>
                <td>${contact.name}</td>
                    <%--底层调用 javabean 的 get 方法--%>
                <td>${contact.sex}</td>
                <td>${contact.age}</td>
                <td>${contact.province}</td>
                <td>${contact.qq}</td>
                <td>${contact.email}</td>
                <td></td>
            </tr>
        </c:forEach>
    </c:if>
</table>

</body>
</html>
