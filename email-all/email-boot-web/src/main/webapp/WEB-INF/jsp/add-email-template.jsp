<%--
  Created by IntelliJ IDEA.
  User: 27497
  Date: 2019/11/5
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table, tr, td, input {
            border: 1px solid green; /*表格,行,列的 边框属性*/
            margin: auto; /*表格居中*/
            width: 1000px; /*表格宽度*/
            text-align: center; /*文本居中*/
            font-size: x-large; /*表格内文本大小*/
            font-weight: 400; /*文字加粗 700*/
            /*font-style: italic;*/ /*文字风格*/
        }

        .left {
            height: 50px;
            width: 200px;
        }

        div { /*盒子*/
            text-align: center;
            margin-top: 30px; /*外上边距*/
            height: 50px;
        }

        input {
            height: 50px;
            width: 200px;
        }
    </style>
    <title>ADD</title>
</head>
<body>
<div>
    <form method="post" action="${pageContext.request.contextPath}/insertOrUpdate">
        <input type="hidden" value="${one.templateId}" name="id">
        <table border="1xp solid black">
            <tr>
                <td class="left">邮件主题</td>
                <td><input type="text" value="${one.subject}" name="subject"></td>
            </tr>
            <tr>
                <td class="left">邮件内容</td>
                <td><textarea rows="5" cols="50" name="content">${one.content}</textarea></td>
            </tr>
        </table>
        <input type="submit" value="submit"/>

    </form>
</div>

</body>
</html>
