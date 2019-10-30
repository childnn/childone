<%--
  Created by IntelliJ IDEA.
  User: 27497
  Date: 2019/11/5
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <style> /*css*/
    div { /*盒子*/
        text-align: center;
        margin-top: 30px; /*外上边距*/
        height: 50px;
    }

    .id {
        width: 10px;
        height: 50px;
        font-size: medium;
    }

    .subject, .head { /*文本框属性,id 选择器*/
        height: 50px;
        /*width: 250px;*/
        text-align: center; /*文字居中*/
        font-size: large; /*文字大小*/
        margin-left: 10px; /*文本框外边距*/
        margin-right: 10px;
        margin-top: 10px;
    }

    #content {
        height: 50px;
        text-align: justify;
        font-size: small;
    }

    #update, #send, #delete {
        height: 50px;
        width: 50px;
        text-align: center;
        font-size: medium;
    }

    table, tr, td {
        border: 1px solid green; /*表格,行,列的 边框属性*/
        margin: auto; /*表格居中*/
        /*width: 1000px; !*表格宽度*!*/
        text-align: center; /*文本居中*/
        font-size: x-large; /*表格内文本大小*/
        font-weight: 400; /*文字加粗 700*/
        /*font-style: italic;*/ /*文字风格*/
    }

    .define {
        font-size: smaller;
    }

    tr { /*行高度*/
        height: 50px;
        /*width: 200px;*/
    }

    td > input { /*复选框大小*/
        width: 25px;
        height: 25px;
    }

    #add { /*按钮大小,id 选择器*/
        width: 100px;
        height: 40px;
        margin-left: 20px;
        margin-right: 20px;
        color: chartreuse; /*按钮文字颜色*/
        background-color: gray; /*按钮背景颜色*/
    }
    </style>
    <title>index</title>
</head>
<body>
<div>
    <caption><h1>邮件模板列表</h1></caption>
    <%--<form action="${pageContext.request.contextPath}/" METHOD="post">--%>
    <table border="1xp solid black">
        <tr>
            <td class="id">模板ID</td>
            <td id="subject">邮件主题</td>
            <td id="head">邮件内容</td>
            <td class="head" colspan="3">操作</td>
            <%--<td id="send"><a href="">使用此模板发送邮件</a></td>--%>
        </tr>
        <tr>
            <td class="head">模板示例</td>
            <td class="head">测试模板</td>
            <td class="head">尊敬的用户，您的 %s 已退款成功，退款金额 %s 元。</td>
            <td class="define" colspan="3">友情提示: 模板中的占位符请使用 %s</td>
            <%--<td id="send"><a href="">使用此模板发送邮件</a></td>--%>
        </tr>
        <c:forEach items="${list}" var="pojo">
            <tr>
                <td class="id">${pojo.templateId}</td>
                <td class="subject">${pojo.subject}</td>
                <td id="content">${pojo.content}</td>
                <td id="update"><a href="${pageContext.request.contextPath}/getOne?id=${pojo.templateId}">修改模板</a></td>
                <td id="delete"><a href="${pageContext.request.contextPath}/delete?id=${pojo.templateId}">删除此模板</a></td>
                <td id="send"><a href="${pageContext.request.contextPath}/send?id=${pojo.templateId}">使用此模板发送邮件</a></td>
            </tr>

        </c:forEach>
    </table>
    <%--<input type="submit" name="submit" id="submit"/>--%>
    <%--</form>--%>
    <a href="${pageContext.request.contextPath}/add" style="font-size: large; color: fuchsia">新增模板</a>
</div>

</body>
</html>
