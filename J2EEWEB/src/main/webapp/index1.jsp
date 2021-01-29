<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/4/1
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>

    <script>

        onload = function () {
            //为验证码图片设置点击事件
            document.getElementById("codeImg").onclick = function () {
                this.src = "${pageContext.request.contextPath}/code?" + new Date().getMilliseconds();

            };
        };
    </script>

</head>
<body>

<form action="${pageContext.request.contextPath}/login">
    <input type="text" placeholder="用户名" name="username"/> <br/>
    <input type="password" placeholder="密码" name="password"/> <br/>
    <img src="${pageContext.request.contextPath}/code" id="codeImg" alt="loading" title="请输入验证码"> <br/>
    <input type="text" name="checkCode"> <br/>
    <input type="submit" value="submit"> <br/>
</form>
<hr/>
<%--信息回显--%>
<h5 style="color: red">${errorMessage}</h5>

</body>
</html>
