JSTL: Java server pages Standard Tag Library
jstl-c 标签报错: <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
需要引入依赖:
    <dependency>
      <groupId>javax.servlet.jsp.jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>

JS 和 jQuery 对象的转换:
     js对象转化为jQuery对象
         var box=document.getElementById("box");
         var $box=$(box);
     jQuery对象转化为js对象
         var box1=$(box)[0];
         var box2=$(box).get(0);
