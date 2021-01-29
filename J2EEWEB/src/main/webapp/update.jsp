<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/29
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>update</title>
</head>
<body>
<%--
            <td>姓名</td>
            <td>性别</td>
            <td>年龄</td>
            <td>籍贯</td>
            <td>QQ</td>
            <td>邮箱</td>
--%>
<%--修改信息界面: 接受待修改 对象 的信息,回显到页面--%>
<h2>update</h2>
<%--把修改 对象的 id 带回去, 便于 update sql 语句的 执行--%>
<form action="${pageContext.request.contextPath}/update?id=${contact.id}" method="post">

    <table style="border: 1px solid red">
        <tr>
            <td>
                <input type="text" name="name" value="${contact.name}">
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" name="sex" value="male"
                       <c:if test="${contact.sex == 'male'}">checked="checked"</c:if>/>male
                <input type="radio" name="sex" value="female"
                       <c:if test="${contact.sex == 'female'}">checked="checked"</c:if>/>female
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="age" value="${contact.age}">
            </td>
        </tr>
        <tr>
            <td>
                <select name="province" id="">
                    <option value="湖南" <c:if test="${contact.province_id == 1}">selected="selected"</c:if>>湖南</option>
                    <option value="湖北" <c:if test="${contact.province_id == 2}">selected="selected"</c:if>>湖北</option>
                    <option value="北京" <c:if test="${contact.province_id == 3}">selected="selected"</c:if>>北京</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="qq" value="${contact.qq}">
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="email" value="${contact.email}">
            </td>
        </tr>

    </table>
    <input type="submit" value="update">

</form>

</body>
</html>
