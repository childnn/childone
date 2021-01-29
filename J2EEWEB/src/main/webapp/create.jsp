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
    <title>create</title>
</head>
<body>
<%--
    添加联系人:
        姓名
        性别
        年龄
        籍贯
        qq
        email
        添加完毕(先将表单信息提交到 CreateServlet),自动跳转 retrieve.jsp
    表单提交: 对于 文本框,提交的就是 输入的信息: value -- 通过 name 属性的值 拿 value (name属性的值就是 key)
        对于非文本框: 需要主动设置 name/ value, 则提交时的数据就是 name属性值--> value
--%>
<h2>添加联系人</h2>
<%--点击提交按钮,把页面的数据 带到 CreateServlet --%>
<form action="${pageContext.request.contextPath}/create" method="get">

    <table style="border: 1px solid red">
        <%--姓名--%>
        <tr>
            <td>
                <input type="text" name="name" placeholder="姓名"/>
            </td>
        </tr>
        <%--性别--%>
        <tr>
            <td>
                <input type="radio" name="sex" value="male"/>male
                <input type="radio" name="sex" value="female"/>female
            </td>
        </tr>
        <%--年龄--%>
        <tr>
            <td>
                <input type="text" name="age" placeholder="年龄">
            </td>
        </tr>
        <%--籍贯--%>
        <tr>
            <td>
                <select name="province_id" id="">
                    <option value="1">湖南</option>
                    <option value="2">湖北</option>
                    <option value="3">北京</option>
                </select>
            </td>
        </tr>
        <%--qq--%>
        <tr>
            <td>
                <input type="text" name="qq" placeholder="qq">
            </td>
        </tr>
        <%--email--%>
        <tr>
            <td>
                <input type="text" name="email" placeholder="email">
            </td>
        </tr>

    </table>

    <input type="submit" value="submit"> <%--提交数据--%>
    <input type="reset" value="reset"> <%--重置--%>

</form>

</body>
</html>
