<%--
  Created by IntelliJ IDEA.
  User: child
  Date: 2019/4/1
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>检索与分页</title>
</head>
<body>
<%--
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Integer province_id;
    private String qq;
    private String email;

--%>
<form action="${pageContext.request.contextPath}/pagesServlet">
    <%--在表单提交时,带回 当前 页: 如果不输入任何数据,提交的 value 是空字符串--%>
    <input type="hidden" name="pageNumber" value="1"/>
    <input type="text" name="name" value="${map.name[0]}" placeholder="姓名"/>
    <input type="text" name="sex" value="${map.sex[0]}" placeholder="性别"/>
    <input type="text" name="age" value="${map.age[0]}" placeholder="年龄"/>
    <input type="text" name="province_id" value="${map.province_id[0]}" placeholder="籍贯"/>
    <input type="text" name="qq" value="${map.qq[0]}" placeholder="QQ"/>
    <input type="text" name="email" value="${map.email[0]}" placeholder="Email"/>
    <input type="submit" value="查询"/>
</form>
<hr/>
<%--展示每页的数据--%>
<table border="1px solid red">
    <tr>
        <td><input type="checkbox" name=""></td>
        <td>编号</td>
        <td>姓名</td>
        <td>性别</td>
        <td>年龄</td>
        <td>籍贯</td>
        <td>QQ</td>
        <td>Email</td>
        <td>操作</td>
    </tr>

    <c:forEach items="${pageBean.list}" var="contact" varStatus="vs">
        <tr>
            <td><input type="checkbox" name="${contact.id}" class=""></td>
            <td>${vs.count}</td>
            <td>${contact.name}</td>
            <td>${contact.sex}</td>
            <td>${contact.age}</td>
            <td>${contact.province_id}</td>
            <td>${contact.qq}</td>
            <td>${contact.email}</td>
            <td>
                <a href="${pageContext.request.contextPath}/update?id=${contact.id}">update</a>
                <a href="${pageContext.request.contextPath}/delete?id=${contact.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<hr/>
<input type="button" value="selectOthers">
<input type="button" value="deleteSelected">
<input type="button" value="deleteAll">


<hr/>
<%--在点击翻页时:带回检索的信息,保证翻页时还带这检索信息--%>
<%--首页/上一页--%>
<c:if test="${pageBean.pageNumber != 1}">
    <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=1&name=${map.name[0]}&sex=${map.sex[0]}&age=${map.age[0]}&province_id=${map.province_id[0]}&qq=${map.qq[0]}&email=${map.email[0]}">首页</a> &nbsp;
    <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=${pageBean.pageNumber - 1}&name=${map.name[0]}&sex=${map.sex[0]}&age=${map.age[0]}&province_id=${map.province_id[0]}&qq=${map.qq[0]}&email=${map.email[0]}">上一页</a> &nbsp;
</c:if>
<%--遍历总页数--%>
<%--<c:forEach begin="1" end="${pageBean.totalPages}" var="page" varStatus="vs">--%>
<c:forEach begin="1" end="${pageBean.totalPages}" var="page" varStatus="vs">
    <%--判断是否是当前页面: 不给当前页面超链接--%>
    <c:choose>
        <%--<c:when test="${pageBean.pageNumber == page}">--%>
        <c:when test="${pageBean.pageNumber == page}">
            ${page} &nbsp;
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=${page}">${page}</a> &nbsp;
        </c:otherwise>
    </c:choose>
</c:forEach>
<%--下一页/尾页--%>
<c:if test="${pageBean.pageNumber != pageBean.totalPages}">
    <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=${pageBean.pageNumber + 1}&name=${map.name[0]}&sex=${map.sex[0]}&age=${map.age[0]}&province_id=${map.province_id[0]}&qq=${map.qq[0]}&email=${map.email[0]}">下一页</a> &nbsp;
    <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=${pageBean.totalPages}&name=${map.name[0]}&sex=${map.sex[0]}&age=${map.age[0]}&province_id=${map.province_id[0]}&qq=${map.qq[0]}&email=${map.email[0]}">尾页</a> &nbsp;
</c:if>
共${pageBean.totalCount}条数据&nbsp;共${pageBean.totalPages}页

</body>
</html>
