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
    <title>pages_search</title>

    <style> /*css*/
    div, div > input { /*盒子*/
        text-align: center;
        margin-top: 30px; /*外上边距*/
        height: 50px;
    }

    #id, #name, #gender { /*文本框属性,id 选择器*/
        height: 50px;
        width: 250px;
        text-align: center; /*文字居中*/
        font-size: large; /*文字大小*/
        margin-left: 10px; /*文本框外边距*/
        margin-right: 10px;
        margin-top: 10px;
    }

    table, tr, td {
        border: 1px solid green; /*表格,行,列的 边框属性*/
        margin: auto; /*表格居中*/
        width: 1000px; /*表格宽度*/
        text-align: center; /*文本居中*/
        font-size: x-large; /*表格内文本大小*/
        font-weight: 400; /*文字加粗 700*/
        /*font-style: italic;*/ /*文字风格*/
    }

    tr { /*行高度*/
        height: 50px;
        /*width: 200px;*/
    }

    td > input { /*复选框大小*/
        width: 25px;
        height: 25px;
    }

    .button { /*按钮大小,类选择器*/
        width: 100px;
        height: 40px;
        margin-left: 20px;
        margin-right: 20px;
        color: chartreuse; /*按钮文字颜色*/
        background-color: gray; /*按钮背景颜色*/
    }
    </style>

    <script> /*js*/
    onload = function () {
        //获取所有 class 值为 "checkbox" 的 元素对象 -- 数组
        const elementsByClassName = document.getElementsByClassName("checkbox");
        /*删除选中的记录*/
        /*
        * 1.判断对应的 行 是否被选中
        * 2.将选中的 行对应的 id (name 属性的值) 赋值到 form 表单的 action 值中
        * 3.不需要判断: submit 会自动提交被选中的 checkbox!?
       * */
        /*    function deleteSelected(obj)  {
               /!* for(let e of elementsByClassName) {
                    if (e.checked){ //如果对应的元素被选中: 获取对应的 name 属性值
                       var form = document.getElementsByTagName("form")[0];
                       form.action += (e.name + "=" + e.value + "&");

                    }
                }*!/
               // var form = document.getElementsByTagName("form")[0];
                alert(form.action); /!*测试:ok*!/
            }*/

        //全选
        document.getElementById("selectAll").onclick = function () {
            for (let e of elementsByClassName) {
                e.checked = true;
            }
        };
        //全不选
        document.getElementById("selectNone").onclick = function () {
            for (let e of elementsByClassName) {
                e.checked = false;
            }
        };
        /**
         * 1.反选
         * 2.设置表头为 false
         * 3.如果反选之前没有 一个选择框被选中,则点击反选, 表头也会被选中
         */
        document.getElementById("selectOthers").onclick = function () {
            let n = 0;
            const first = document.getElementById("first");
            for (let e of elementsByClassName) {
                // alert(elementsByName[0].checked); //测试
                if (e.checked === false) { //判断 选择框是否被选中,未被选中则计数
                    n++;
                }
                e.checked = !e.checked;
            }
            first.checked = (n === elementsByClassName.length); //如果点击 反选 之前,没有一个被选中, 点击反选后,反选 也会被选中
        };
        //first : 添加信息后, 本按钮就失效了?
        /*elementsByName[0].onclick = function () {
            for (let index of elementsByName) {
                index.checked = this.checked; //其他选项的状态和表头一致
            }
         };*/
        //表头选择
        document.getElementById("first").onclick = function () {
            for (let e of elementsByClassName) {
                e.checked = this.checked;
            }
        };
        /* function change(obj) {
             //for-in : 可以遍历对象中的各种元素
             /!* for (var index in elementsByName) {
                  elementsByName[index].checked = first;
              }*!/
             //for-of
             for (const e of elementsByClassName) {
                 e.checked = obj.checked;
             }
         }*/

        //改变 tr 颜色
        function addColor(obj) {
            // obj.style.backgroundColor = "green"; //方法一
            obj.setAttribute("style", "background-color: SpringGreen ");
        }

        function removeColor(obj) {
            obj.removeAttribute("style");
        }

        /* //设置添加文本框的属性: 聚焦时, placeholder 消失
         const ing = document.getElementsByClassName("ing");
         for (let index in ing) {
             ing[index].onfocus = function () {
                 this.placeholder = "";
             };
             ing[0].onblur = function () {
                 this.placeholder = "请输入id";
             };
             ing[1].onblur = function () {
                 this.placeholder = "请输入姓名";
             };
             ing[2].onblur = function () {
                 this.placeholder = "请输入性别";
             };
         }*/
    };

    </script>

</head>
<body>

<form action="${pageContext.request.contextPath}/pages">
    <div>
        <input type="hidden" name="pageNumber" value="1"> <%--条件查询,初始就是第一页--%>
        <%--条件查询: 回显--%>
        <input type="text" name="name" value="${name}" placeholder="姓名">
        <input type="text" name="sex" placeholder="性别">
        <input type="text" name="age" placeholder="年龄">
        <input type="text" name="province" placeholder="籍贯">
        <input type="text" name="qq" placeholder="QQ">
        <input type="text" name="email" placeholder="email">
        <input type="submit" value="查询" id="bt"> <%--利用点击事件提交--%>
    </div>
</form>

<%-- 点击 deleteSelected 提交所选的 id--%>
<form action="${pageContext.request.contextPath}/DeleteSelectedServlet" method="post">

    <%--本页面显示所有联系人: ok--%>
    <table border="1xp solid black">

        <caption><h1>联系人</h1></caption>

        <tr>
            <td><input type="checkbox" class="checkbox" id="first"/></td><!--全选-->
            <td>编号</td>
            <td>姓名</td>
            <td>性别</td>
            <td>年龄</td>
            <td>籍贯</td>
            <td>QQ</td>
            <td>邮箱</td>
            <%--<td>操作</td>--%>
        </tr>
        <%--在各个域中只有一个对应的属性时,可以直接写响应的属性名(key值), 如,此处的 list 可以直接写, 为了便于区分,也可以加上其所属的域对象名--%>
        <c:if test="${not empty pageBean.list}"><%--非空校验--%>
            <c:forEach items="${pageBean.list}" var="contact" varStatus="vs">
                <%--遍历显示 list 中的 对象--%>
                <tr onmouseover="addColor(this)" onmouseout="removeColor(this)">
                        <%--在 checkbox 提交时, 提交的内容就是 name:value,
                        没有 name,数据不会提交,没有 value 默认为 value="on"
                        如果写了 value="", 则表示,value 值为 空字符串--%>
                    <td><input type="checkbox" class="checkbox" name="${contact.id}"/></td>
                    <td>${vs.count}</td>
                    <td>${contact.name}</td>
                        <%--底层调用 javabean 的 get 方法--%>
                    <td>${contact.sex}</td>
                    <td>${contact.age}</td>
                    <td>${contact.province_id}</td>
                    <td>${contact.qq}</td>
                    <td>${contact.email}</td>
                        <%-- <td>
                             <a href="${pageContext.request.contextPath}/getUpdate?id=${contact.id}">update</a> &lt;%&ndash;修改&ndash;%&gt;
                             <a href="${pageContext.request.contextPath}/delete?id=${contact.id}">delete</a> &lt;%&ndash;删除&ndash;%&gt;
                         </td>--%>
                </tr>
            </c:forEach>
        </c:if>

    </table>
    <%--分页: 不知道具体有多少页,怎么做?--%>
    <div>

        <c:if test="${0 != pageBean.totalPages && pageBean.pageNumber != 1}"> <%--不是首页时,再显示"首页"--%>
            <%--条件检索时,点击下一页,也要带着检索条件提交--%>
            <%--<a href="${pageContext.request.contextPath}/pages?pageNumber=1&name=${map.name[0]}&sex=${map.sex[0]}&age=${map.age[0]}&province=${map.province[0]}&qq=${map.qq[0]}&email=${map.email[0]}">首页</a>--%>
            <a href="${pageContext.request.contextPath}/pagesServlet?pageNumber=1&name=${name}&qq=${qq}">首页</a>
            <a href="${pageContext.request.contextPath}/pages?pageNumber=${pageBean.pageNumber - 1}&name=${name}&qq=${qq}">上一页</a>
        </c:if>

        <c:forEach begin="1" end="${pageBean.totalPages}" step="1" var="page">
            <c:if test="${pageBean.pageNumber != page}">
                <a href="${pageContext.request.contextPath}/pages?pageNumber=${page}&name=${name}&qq=${qq}">${page}&nbsp;</a>
            </c:if>
            <%--当前页不设超链接:已经处在当前页面,就不能再点击当前页--%>
            <c:if test="${pageBean.pageNumber == page}">
                ${page}&nbsp;
            </c:if>
        </c:forEach>

        <c:if test="${0 != pageBean.totalPages && pageBean.pageNumber != pageBean.totalPages}">
            <a href="${pageContext.request.contextPath}/pages?pageNumber=${pageBean.pageNumber + 1}&name=${name}&qq=${qq}">下一页&nbsp;</a>
            <a href="${pageContext.request.contextPath}/pages?pageNumber=${pageBean.totalPages}&name=${name}&qq=${qq}">尾页&nbsp;</a>
        </c:if>
        共${pageBean.totalCount}条数据
        共${pageBean.totalPages}页

    </div>

    <%--如果 联系人为空,显示 下面的信息--%>
    <h3 align="center" style="color: red"><c:if
            test="${empty pageBean.list}">联系人为空,点击添加按钮添加联系人</c:if></h3> <%--来自 DeleteAllServlet 的数据--%>

    <p align="center"> <!--与表格分段-->
        <%--添加数据--%>
        <a href="${pageContext.request.contextPath}/create.jsp">
            <input type="button" value="create" class="button"/>
        </a>
        <a href="${pageContext.request.contextPath}/deleteAll"> <%--删除所有数据--%>
            <input type="button" id="selectAll" value="deleteAll" class="button"/>
        </a>
        <input type="button" id="selectNone" value="selectNone" class="button"/>
        <input type="button" id="selectOthers" value="selectOthers" class="button"/>
        <input type="submit" id="deleteSelected" value="deleteSelected" class="button"/>
    </p>

    <%--<div> &lt;%&ndash;检索查询: 包括模糊查询&ndash;%&gt;
            &lt;%&ndash;查询指定用户信息： 给定多个信息,可能查到多条数据
                关键: dao 层 的 sql 拼接
            &ndash;%&gt;
        <input type="text" name="name" placeholder="姓名">
        <input type="text" name="sex" placeholder="性别">
        <input type="text" name="age" placeholder="年龄">
        <input type="text" name="province" placeholder="籍贯">
        <input type="text" name="qq" placeholder="QQ">
        <input type="text" name="email" placeholder="email">
        <input type="button" value="查询" id="bt"> &lt;%&ndash;利用点击事件提交&ndash;%&gt;
    </div>--%>

</form>

<%--<script>

    //获取所有 class 值为 "checkbox" 的 元素对象 -- 数组
    const elementsByClassName = document.getElementsByClassName("checkbox");
    /*删除选中的记录*/
    /*
    * 1.判断对应的 行 是否被选中
    * 2.将选中的 行对应的 id (name 属性的值) 赋值到 form 表单的 action 值中
    * 3.不需要判断: submit 会自动提交被选中的 checkbox!?
   * */
/*    function deleteSelected(obj)  {
       /!* for(let e of elementsByClassName) {
            if (e.checked){ //如果对应的元素被选中: 获取对应的 name 属性值
               var form = document.getElementsByTagName("form")[0];
               form.action += (e.name + "=" + e.value + "&");
            }
        }*!/
       // var form = document.getElementsByTagName("form")[0];
        alert(form.action); /!*测试:ok*!/
    }*/

    //全选
    document.getElementById("selectAll").onclick = function () {
        for (let e of elementsByClassName) {
            e.checked = true;
        }
    };
    //全不选
    document.getElementById("selectNone").onclick = function () {
        for (let e of elementsByClassName) {
            e.checked = false;
        }
    };
    /**
     * 1.反选
     * 2.设置表头为 false
     * 3.如果反选之前没有 一个选择框被选中,则点击反选, 表头也会被选中
     */
    document.getElementById("selectOthers").onclick = function () {
        let n = 0;
        const first = document.getElementById("first");
        for (let e of elementsByClassName) {
            // alert(elementsByName[0].checked); //测试
            if (e.checked === false) { //判断 选择框是否被选中,未被选中则计数
                n++;
            }
            e.checked = !e.checked;
        }
        first.checked = (n === elementsByClassName.length); //如果点击 反选 之前,没有一个被选中, 点击反选后,反选 也会被选中
    };
    //first : 添加信息后, 本按钮就失效了?
    /*elementsByName[0].onclick = function () {
        for (let index of elementsByName) {
            index.checked = this.checked; //其他选项的状态和表头一致
        }
     };*/
    //表头选择
    function change(obj) {
        //for-in : 可以遍历对象中的各种元素
        /* for (var index in elementsByName) {
             elementsByName[index].checked = first;
         }*/
        //for-of
        for (const e of elementsByClassName) {
            e.checked = obj.checked;
        }
    }
    //改变 tr 颜色
    function addColor(obj) {
        // obj.style.backgroundColor = "green"; //方法一
        obj.setAttribute("style", "background-color: SpringGreen ");
    }
    function removeColor(obj) {
        obj.removeAttribute("style");
    }
    //设置添加文本框的属性: 聚焦时, placeholder 消失
    const ing = document.getElementsByClassName("ing");
    for (let index in ing) {
        ing[index].onfocus = function () {
            this.placeholder = "";
        };
        ing[0].onblur = function () {
            this.placeholder = "请输入id";
        };
        ing[1].onblur = function () {
            this.placeholder = "请输入姓名";
        };
        ing[2].onblur = function () {
            this.placeholder = "请输入性别";
        };
    }
</script>--%>

</body>
</html>
