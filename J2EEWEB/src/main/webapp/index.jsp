<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/3/22
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>

<%--<form action="http://localhost:8080${pageContext.request.contextPath}/login01" method="get">
    用户名: <input type="text" name="username" />
    密码: <input type="password" name="passwords" />
  <input type="submit" value="登录"/>
</form>--%>
<h1><a href="${pageContext.request.contextPath}/ServletResponse0">验证码</a></h1>

<a href="${pageContext.request.contextPath}/ServletContextDemo">点击存储数据到 本项目的 ServletContext 对象</a>
<a href="${pageContext.request.contextPath}/ServletContextDemo01">点击测试数据共享</a>

<a href="${pageContext.request.contextPath}/cs">统计次数</a>
<a href="${pageContext.request.contextPath}/ss">展示次数</a>
<br/> <br/><br/>
<a href="${pageContext.request.contextPath}/download.html">点一下</a>

<a href="${pageContext.request.contextPath}/jsp/demo.jsp">查看</a>
<br/>
<a href="${pageContext.request.contextPath}/ss1">点击跳转ss2</a>
<a href="${pageContext.request.contextPath}/ss2">点击访问真正的ss2</a>
<a href="${pageContext.request.contextPath}/ss3">点击访问ss3</a><br/>

<a href="${pageContext.request.contextPath}/jsp/jstl.jsp">点击跳转jstl1.jsp</a>
<a href="${pageContext.request.contextPath}/jsp/jstl2.jsp">点击跳转jstl3.jsp</a><br/>

<a href="${pageContext.request.contextPath}/jsp/jstl4.jsp">点击跳转jstl5.jsp</a>
<a href="${pageContext.request.contextPath}/jsp/jstl6.jsp">点击跳转jstl7.jsp</a>
<a href="${pageContext.request.contextPath}/jsp/jstl8.jsp">点击跳转jstl9.jsp</a>

<a href="${pageContext.request.contextPath}/sc3">ServletContext3</a>
<br/>

<hr/>
<a href="${pageContext.request.contextPath}/el_jstl/el_jstl04.jsp">web/el_jstl</a>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/js_ajax/demo1.jsp">js_ajax</a>
<a href="${pageContext.request.contextPath}/jq_ajax/demo01.jsp">jq_ajax($.get())</a>
<a href="${pageContext.request.contextPath}/jq_ajax/demo02.jsp">jq_ajax($.post())</a>
<a href="${pageContext.request.contextPath}/jq_ajax/demo03.jsp">jq_ajax($.ajax())</a> <br/>
<a href="${pageContext.request.contextPath}/jq_ajax/demo04.jsp">jq3.0_ajax($.get())</a>
<a href="${pageContext.request.contextPath}/jq_ajax/demo05.jsp">jq3.0_ajax($.post())</a>
<hr/>
<h2>案例一:异步校验注册用户名</h2>
<a href="${pageContext.request.contextPath}/jq_ajax/demo/AsyncCheck.jsp">点击进入注册页面</a>
</body>
<hr/>


<a href="${pageContext.request.contextPath}/json/demo01.jsp">json01</a>
<a href="${pageContext.request.contextPath}/json/demo02.html">度娘一下</a>
<br/>
<br/>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/filter/demo.jsp">filter</a>
<br/>
<br/>
<br/>
<br/>
<br/>

<%--接收 WebServlet 端的 请求转发，判断 msg 是否为 null 如果是，在页面不显示任何东西--%>
<span style="color: red"><%= request.getAttribute("error") == null ? "" : request.getAttribute("error")%></span>

<form action="/day23/ws" method="post">
    用户名<input type="text" name="username" placeholder="用户名"/> <br/>
    密码<input type="text" name="passwords" placeholder="密码"/> <br/>
    验证码<input type="text" name="checkCode" placeholder="验证码"> <br/>
    <a href=""><img src="/day23/cs" alt="" title="看不清换一张"></a><br/>
    <input type="submit" value="submit"/>
</form>

<%--
   1.首页登录界面: 用户名,密码,验证码  -- index.jsp
   2.登录成功跳转 用户界面:  -- welcome.jsp
          <a>查看所有用户</a> (retrieve)  --  跳转 retrieve.jsp
          <a>添加用户</a> (create) -- 跳转 create.jsp (表单: 提交添加数据)
                  -- 添加完毕 自动跳转 展示 页面 retrieve.jsp 显示最新数据
   3.删除用户: (delete) 在 retrieve.jsp 页面显示
        删除完毕自动返回 retrieve.jsp 最新数据
   4.修改: (update)
--%>
<form action="${pageContext.request.contextPath}/web" method="post">
    用户名 <input type="text" name="username" placeholder="用户名"><br/>
    密码 <input type="password" name="password" placeholder="密码"><br/>
    <a href=""><img src="${pageContext.request.contextPath}/code"></a><br/>
    验证码 <input type="text" name="checkCode" placeholder="验证码"><br/>
    <input type="submit" value="submit"><br/>
</form>
<hr/>
<span>${sessionScope.error}</span>

<a href="${pageContext.request.contextPath}/checkBox.jsp">checkBox</a>
<%--分页查看--%>
<a href="${pageContext.request.contextPath}/pages?pageNumber=1">pages</a>

<%--<a href="${pageContext.request.contextPath}/retrieve">点击查看所有用户</a>--%>
<a href="${pageContext.request.contextPath}/test.jsp">test</a>

</html>
