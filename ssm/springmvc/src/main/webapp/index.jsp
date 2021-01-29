<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/4/19 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <%--会被 DispatcherServlet 拦截: 必须要配置: springmvc.xml--%>
</head>
<body>

<a href="test.jsp">test.jsp</a>

<hr/>

<%--简单数据: 变量名一致即可: name=方法参数变量名--%>
<form action="user/test">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <input type="submit" value="简单数据"/>
</form>
<hr/>

<%--对象:name 属性值 = 对象的 成员变量名--%>
<form action="user/test1">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <br/>
    日期<input type="text" name="date"/>
    <input type="submit" value="对象"/>
</form>
<hr/>


<%--对象的 list 集合 数据的封装: name 属性值 == 对象的集合属性名[索引].属性名--%>
<form action="user/test2">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <br/>
    第一个账户名: <input type="text" name="accounts[0].name"/>
    第一个账户金额:<input type="text" name="accounts[0].money"/>
    <br/>
    第二个账户名: <input type="text" name="accounts[1].name"/>
    第二个账户金额:<input type="text" name="accounts[1].money"/>
    <br/>
    第三个账户名: <input type="text" name="accounts[2].name"/>
    第三个账户金额:<input type="text" name="accounts[2].money"/>
    <br/>
    <input type="submit" value="对象的list集合"/>
</form>
<hr/>


<%--Map<String, Account> : name 属性值 == map 集合属性名[key值].属性名 --%>
<form action="user/test3">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <br/>
    第一个账户名: <input type="text" name="maps['1'].name"/>
    第一个账户金额:<input type="text" name="maps['1'].money"/>
    <br/>
    第二个账户名: <input type="text" name="maps[2].name"/>
    第二个账户金额:<input type="text" name="maps[2].money"/>
    <br/>
    第三个账户名: <input type="text" name="maps[3].name"/>
    第三个账户金额:<input type="text" name="maps[3].money"/>
    <br/>
    <input type="submit" value="对象的map集合"/>
</form>
<hr/>

<%--servlet--%>

<a href="user/test4">servlet</a>

<hr/>
<hr/>

<%--获取请求头--%>
<a href="user/test5">requestHeader</a>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>


<%--org.anonymous.controller.UserController--%>
<form action="test1" method="post">
    <input type="text" name="id">
    <input type="text" name="username">
    <input type="text" name="password">
    <input type="submit" value="submit">
</form>
<hr/>
<a href="save">saveSession</a>
<a href="find">findSession</a>
<a href="find0">findSession0</a>
<a href="delete">deleteSession</a>
<br/>
<br/>
<br/>

RestFul 编程风格：
<form action="restFul/user/1" method="get">
    <input type="submit" value="get获取">
</form>

<form action="restFul/user/2" method="post">
    <input type="submit" value="post增加">
</form>

<hr/>

<input type="button" id="bt" value="ajax">

<br/>
<a href="string/test1">string类型返回值:实现重定向/请求转发</a><br/>
<a href="string/test2">void类型返回值:实现重定向/请求转发</a><br/>
<a href="string/test3">ModelAndView 返回值实现 数据响应/页面跳转(重定向/请求转发)</a><br/>

<hr/>

<%--上传--%>
<form action="upload/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="myFile">
    <input type="submit" value="upload">
</form>

<hr/>

<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="myFile">
    <input type="submit" value="跨服务器上传">
</form>
<hr/>
<%--异常处理--%>
<a href="test1">异常处理</a>

<%--拦截器--%>
<a href="test2">拦截器测试</a>

<%--拦截器案例: 用户校验--%>
<a href="find">查看用户信息</a>


<%--给 ajax 按钮绑定点击事件--%>
<script>
    $("#bt").click(function () {
        $.ajax({
            url: '/springMVC_day02_a/ajax',
            data: '{"id":1, "username":"jack", "password":"abcd"}',
            type: 'post',
            dataType: "json",
            contentType: 'application/json;charset=utf8',
            sync: true,
            success: function (d) {
                alert(d.username);
            },
            error: function () {
                alert("error..")
            }
        })
    });
</script>


</body>
</html>
