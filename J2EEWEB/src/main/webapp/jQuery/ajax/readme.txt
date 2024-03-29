低音深沉,中音浑厚,高音明亮

哪个 jQuery 方法用于添加或删除被选元素的一个或多个类？
    toggleClass()
$("div#intro .head") 选择器选取哪些元素？
    id="intro" 的首个 div 元素中的 class="head" 的所有元素

AJAX: Asynchronous JavaScript And XML
AJAX 不是新的编程语言，而是一种使用现有标准的新方法。
AJAX 是在不重新加载整个页面的情况下与服务器交换数据并更新部分网页的技术。

XMLHttpRequest 是 AJAX 的基础。

GET - 从指定的资源请求数据。
POST - 向指定的资源提交要被处理的数据

GET:
    请注意，查询字符串（名称/值对）是在 GET 请求的 URL 中发送的：
    /test/demo_form.asp?name1=value1&name2=value2
    有关 GET 请求的其他一些注释：
        GET 请求可被缓存
        GET 请求保留在浏览器历史记录中
        GET 请求可被收藏为书签
        GET 请求不应在处理敏感数据时使用; 只允许 ASCII 字符。
        GET 请求有长度限制
        GET 请求只应当用于取回数据
POST:
    请注意，查询字符串（名称/值对）是在 POST 请求的 HTTP 消息主体中发送的：
    POST /test/demo_form.asp HTTP/1.1
    Host: w3schools.com
    name1=value1&name2=value2
    有关 POST 请求的其他一些注释：
        POST 请求不会被缓存
        POST 请求不会保留在浏览器历史记录中
        POST 不能被收藏为书签
        POST 请求对数据长度没有要求; 允许二进制数据。
----------------------------------------------------------------------------------------
选择器                    实例                      选取
  *	                 $("*")	                    所有元素
#id	                $("#lastname")	        id="lastname" 的元素
.class	            $(".intro")	            所有 class="intro" 的元素
element	            $("p")	                  所有 <p> 元素
.class.class	    $(".intro.demo")	    所有 class="intro" 且 class="demo" 的元素

:first	            $("p:first")	        第一个 <p> 元素
:last	            $("p:last")	            最后一个 <p> 元素
:even	            $("tr:even")	        所有偶数 <tr> 元素
:odd	            $("tr:odd")	            所有奇数 <tr> 元素

:eq(index)	        $("ul li:eq(3)")	    列表中的第四个元素（index 从 0 开始）
:gt(no)	            $("ul li:gt(3)")	    列出 index 大于 3 的元素
:lt(no)	            $("ul li:lt(3)")	    列出 index 小于 3 的元素
:not(selector)	    $("input:not(:empty)")	所有不为空的 input 元素

:header	            $(":header")	        所有标题元素 <h1> - <h6>
:animated	 	                            所有动画元素

:contains(text)	    $(":contains('W3School')")	包含指定字符串的所有元素
:empty	            $(":empty")	            无子（元素）节点的所有元素
:hidden	            $("p:hidden")	        所有隐藏的 <p> 元素
:visible	        $("table:visible")	    所有可见的表格

s1,s2,s3	        $("th,td,.intro")	    所有带有匹配选择的元素

[attribute]	        $("[href]")	            所有带有 href 属性的元素
[attribute=value]	$("[href='#']")	        所有 href 属性的值等于 "#" 的元素
[attribute!=value]	$("[href!='#']")	    所有 href 属性的值不等于 "#" 的元素
[attribute$=value]	$("[href$='.jpg']")	    所有 href 属性的值包含以 ".jpg" 结尾的元素

:input	            $(":input")	            所有 <input> 元素
:text	            $(":text")	            所有 type="text" 的 <input> 元素
:password	        $(":password")	        所有 type="password" 的 <input> 元素
:radio	            $(":radio")	            所有 type="radio" 的 <input> 元素
:checkbox	        $(":checkbox")	        所有 type="checkbox" 的 <input> 元素
:submit	            $(":submit")	        所有 type="submit" 的 <input> 元素
:reset	            $(":reset")	            所有 type="reset" 的 <input> 元素
:button	            $(":button")	        所有 type="button" 的 <input> 元素
:image	            $(":image")	            所有 type="image" 的 <input> 元素
:file	            $(":file")	            所有 type="file" 的 <input> 元素

:enabled	        $(":enabled")	        所有激活的 input 元素
:disabled	        $(":disabled")	        所有禁用的 input 元素
:selected	        $(":selected")	        所有被选取的 input 元素
:checked	        $(":checked")	        所有被选中的 input 元素
--------------------------------------------------------------------------------------
事件: event
jQuery 事件方法
    事件方法会触发匹配元素的事件，或将函数绑定到所有匹配元素的某个事件。
触发实例：
    $("button#demo").click()
    上面的例子将触发 id="demo" 的 button 元素的 click 事件。
绑定实例：
    $("button#demo").click(function(){$("img").hide()})
    上面的例子会在点击 id="demo" 的按钮时隐藏所有图像。
----
 方法                     描述
bind()	            向匹配元素附加一个或更多事件处理器
blur()	            触发、或将函数绑定到指定元素的 blur 事件
change()	        触发、或将函数绑定到指定元素的 change 事件
click()	            触发、或将函数绑定到指定元素的 click 事件
dblclick()	        触发、或将函数绑定到指定元素的 double click 事件
delegate()	        向匹配元素的当前或未来的子元素附加一个或多个事件处理器
die()	            移除所有通过 live() 函数添加的事件处理程序。
error()	            触发、或将函数绑定到指定元素的 error 事件
event.isDefaultPrevented()	返回 event 对象上是否调用了 event.preventDefault()。
event.pageX	        相对于文档左边缘的鼠标位置。
event.pageY	        相对于文档上边缘的鼠标位置。
event.preventDefault()	阻止事件的默认动作。
event.result	    包含由被指定事件触发的事件处理器返回的最后一个值。
event.target	    触发该事件的 DOM 元素。
event.timeStamp	    该属性返回从 1970 年 1 月 1 日到事件发生时的毫秒数。
event.type	        描述事件的类型。
event.which	        指示按了哪个键或按钮。
focus()	            触发、或将函数绑定到指定元素的 focus 事件
keydown()	        触发、或将函数绑定到指定元素的 key down 事件
keypress()	        触发、或将函数绑定到指定元素的 key press 事件
keyup()	            触发、或将函数绑定到指定元素的 key up 事件
live()	            为当前或未来的匹配元素添加一个或多个事件处理器
load()	            触发、或将函数绑定到指定元素的 load 事件
mousedown()	        触发、或将函数绑定到指定元素的 mouse down 事件
mouseenter()	    触发、或将函数绑定到指定元素的 mouse enter 事件
mouseleave()	    触发、或将函数绑定到指定元素的 mouse leave 事件
mousemove()	        触发、或将函数绑定到指定元素的 mouse move 事件
mouseout()	        触发、或将函数绑定到指定元素的 mouse out 事件
mouseover()	        触发、或将函数绑定到指定元素的 mouse over 事件
mouseup()	        触发、或将函数绑定到指定元素的 mouse up 事件
one()	            向匹配元素添加事件处理器。每个元素只能触发一次该处理器。
ready()	            文档就绪事件（当 HTML 文档就绪可用时）
resize()	        触发、或将函数绑定到指定元素的 resize 事件
scroll()	        触发、或将函数绑定到指定元素的 scroll 事件
select()	        触发、或将函数绑定到指定元素的 select 事件
submit()	        触发、或将函数绑定到指定元素的 submit 事件
toggle()	        绑定两个或多个事件处理器函数，当发生轮流的 click 事件时执行。
trigger()	        所有匹配元素的指定事件
triggerHandler()	第一个被匹配元素的指定事件
unbind()	        从匹配元素移除一个被添加的事件处理器
undelegate()	    从匹配元素移除一个被添加的事件处理器，现在或将来
unload()	        触发、或将函数绑定到指定元素的 unload 事件
------------------------------------------------------------------------------
jQuery 的效果函数
方法                   描述
animate()	        对被选元素应用“自定义”的动画
clearQueue()	    对被选元素移除所有排队的函数（仍未运行的）
delay()	            对被选元素的所有排队函数（仍未运行）设置延迟
dequeue()	        运行被选元素的下一个排队函数
fadeIn()	        逐渐改变被选元素的不透明度，从隐藏到可见
fadeOut()	        逐渐改变被选元素的不透明度，从可见到隐藏
fadeTo()	        把被选元素逐渐改变至给定的不透明度
hide()	            隐藏被选的元素
queue()	            显示被选元素的排队函数
show()	            显示被选的元素
slideDown()	        通过调整高度来滑动显示被选元素
slideToggle()	    对被选元素进行滑动隐藏和滑动显示的切换
slideUp()	        通过调整高度来滑动隐藏被选元素
stop()	            停止在被选元素上运行动画
toggle()	        对被选元素进行隐藏和显示的切换
---------------------------------------------------------------------------
jQuery 文档操作方法
    方法	            描述
addClass()	    向匹配的元素添加指定的类名。
after()	        在匹配的元素之后插入内容。
append()	    向匹配元素集合中的每个元素结尾插入由参数指定的内容。
appendTo()	    向目标结尾插入匹配元素集合中的每个元素。
attr()	        设置或返回匹配元素的属性和值。
before()	    在每个匹配的元素之前插入内容。
clone()	        创建匹配元素集合的副本。
detach()	    从 DOM 中移除匹配元素集合。
empty()	        删除匹配的元素集合中所有的子节点。
hasClass()	    检查匹配的元素是否拥有指定的类。
html()	        设置或返回匹配的元素集合中的 HTML 内容。
insertAfter()	把匹配的元素插入到另一个指定的元素集合的后面。
insertBefore()	把匹配的元素插入到另一个指定的元素集合的前面。
prepend()	    向匹配元素集合中的每个元素开头插入由参数指定的内容。
prependTo()	    向目标开头插入匹配元素集合中的每个元素。
remove()	    移除所有匹配的元素。
removeAttr()	从所有匹配的元素中移除指定的属性。
removeClass()	从所有匹配的元素中删除全部或者指定的类。
replaceAll()	用匹配的元素替换所有匹配到的元素。
replaceWith()	用新内容替换匹配的元素。
text()	        设置或返回匹配元素的内容。
toggleClass()	从匹配的元素中添加或删除一个类。
unwrap()	    移除并替换指定元素的父元素。
val()	        设置或返回匹配元素的值。
wrap()	        把匹配的元素用指定的内容或元素包裹起来。
wrapAll()	    把所有匹配的元素用指定的内容或元素包裹起来。
wrapinner()	    将每一个匹配的元素的子内容用指定的内容或元素包裹起来。
---------------------------------------------------------------------------
jQuery 属性操作方法
下面列出的这些方法获得或设置元素的 DOM 属性。
这些方法对于 XML 文档和 HTML 文档均是适用的，除了：html()。
    方法	        描述
addClass()	    向匹配的元素添加指定的类名。
attr()	        设置或返回匹配元素的属性和值。
hasClass()	    检查匹配的元素是否拥有指定的类。
html()	        设置或返回匹配的元素集合中的 HTML 内容。
removeAttr()	从所有匹配的元素中移除指定的属性。
removeClass()	从所有匹配的元素中删除全部或者指定的类。
toggleClass()	从匹配的元素中添加或删除一个类。
val()	        设置或返回匹配元素的值。
-----------------------------------------------------------------------------
jQuery CSS 操作函数
下面列出的这些方法设置或返回元素的 CSS 相关属性。
方法                  描述
css()	        设置或返回匹配元素的样式属性。
height()	    设置或返回匹配元素的高度。
offset()	    返回第一个匹配元素相对于文档的位置。
offsetParent()	返回最近的定位祖先元素。
position()	    返回第一个匹配元素相对于父元素的位置。
scrollLeft()	设置或返回匹配元素相对滚动条左侧的偏移。
scrollTop()	    设置或返回匹配元素相对滚动条顶部的偏移。
width()	        设置或返回匹配元素的宽度。
-----------------------------------------------------------------------------
jQuery Ajax 操作函数
jQuery 库拥有完整的 Ajax 兼容套件。其中的函数和方法允许我们在不刷新浏览器的情况下从服务器加载数据。
    方法                 描述
jQuery.ajax()	    执行异步 HTTP (Ajax) 请求。
.ajaxComplete()	    当 Ajax 请求完成时注册要调用的处理程序。这是一个 Ajax 事件。
.ajaxError()	    当 Ajax 请求完成且出现错误时注册要调用的处理程序。这是一个 Ajax 事件。
.ajaxSend()	        在 Ajax 请求发送之前显示一条消息。
jQuery.ajaxSetup()	设置将来的 Ajax 请求的默认值。
.ajaxStart()	    当首个 Ajax 请求完成开始时注册要调用的处理程序。这是一个 Ajax 事件。
.ajaxStop()	        当所有 Ajax 请求完成时注册要调用的处理程序。这是一个 Ajax 事件。
.ajaxSuccess()	    当 Ajax 请求成功完成时显示一条消息。
jQuery.get()	    使用 HTTP GET 请求从服务器加载数据。
jQuery.getJSON()	使用 HTTP GET 请求从服务器加载 JSON 编码数据。
jQuery.getScript()	使用 HTTP GET 请求从服务器加载 JavaScript 文件，然后执行该文件。
.load()	            从服务器加载数据，然后把返回到 HTML 放入匹配元素。
jQuery.param()	    创建数组或对象的序列化表示，适合在 URL 查询字符串或 Ajax 请求中使用。
jQuery.post()	    使用 HTTP POST 请求从服务器加载数据。
.serialize()	    将表单内容序列化为字符串。
.serializeArray()	序列化表单元素，返回 JSON 数据结构数据。
-----------------------------------------------------------------------------
jQuery 遍历函数
jQuery 遍历函数包括了用于筛选、查找和串联元素的方法。
    函数	            描述
.add()	        将元素添加到匹配元素的集合中。
.andSelf()	    把堆栈中之前的元素集添加到当前集合中。
.children()	    获得匹配元素集合中每个元素的所有子元素。
.closest()	    从元素本身开始，逐级向上级元素匹配，并返回最先匹配的祖先元素。
.contents()	    获得匹配元素集合中每个元素的子元素，包括文本和注释节点。
.each()	        对 jQuery 对象进行迭代，为每个匹配元素执行函数。
.end()	        结束当前链中最近的一次筛选操作，并将匹配元素集合返回到前一次的状态。
.eq()	        将匹配元素集合缩减为位于指定索引的新元素。
.filter()	    将匹配元素集合缩减为匹配选择器或匹配函数返回值的新元素。
.find()	        获得当前匹配元素集合中每个元素的后代，由选择器进行筛选。
.first()	    将匹配元素集合缩减为集合中的第一个元素。
.has()	        将匹配元素集合缩减为包含特定元素的后代的集合。
.is()	        根据选择器检查当前匹配元素集合，如果存在至少一个匹配元素，则返回 true。
.last()	        将匹配元素集合缩减为集合中的最后一个元素。
.map()	        把当前匹配集合中的每个元素传递给函数，产生包含返回值的新 jQuery 对象。
.next()	        获得匹配元素集合中每个元素紧邻的同辈元素。
.nextAll()	    获得匹配元素集合中每个元素之后的所有同辈元素，由选择器进行筛选（可选）。
.nextUntil()	获得每个元素之后所有的同辈元素，直到遇到匹配选择器的元素为止。
.not()	        从匹配元素集合中删除元素。
.offsetParent()	获得用于定位的第一个父元素。
.parent()	    获得当前匹配元素集合中每个元素的父元素，由选择器筛选（可选）。
.parents()	    获得当前匹配元素集合中每个元素的祖先元素，由选择器筛选（可选）。
.parentsUntil()	获得当前匹配元素集合中每个元素的祖先元素，直到遇到匹配选择器的元素为止。
.prev()	        获得匹配元素集合中每个元素紧邻的前一个同辈元素，由选择器筛选（可选）。
.prevAll()	    获得匹配元素集合中每个元素之前的所有同辈元素，由选择器进行筛选（可选）。
.prevUntil()	获得每个元素之前所有的同辈元素，直到遇到匹配选择器的元素为止。
.siblings()	    获得匹配元素集合中所有元素的同辈元素，由选择器筛选（可选）。
.slice()	    将匹配元素集合缩减为指定范围的子集。
-----------------------------------------------------------------------------
jQuery 数据操作函数
这些方法允许我们将指定的 DOM 元素与任意数据相关联。
    函数	                描述
.clearQueue()	    从队列中删除所有未运行的项目。
.data()	            存储与匹配元素相关的任意数据。
jQuery.data()	    存储与指定元素相关的任意数据。
.dequeue()	        从队列最前端移除一个队列函数，并执行它。
jQuery.dequeue()	从队列最前端移除一个队列函数，并执行它。
jQuery.hasData()	存储与匹配元素相关的任意数据。
.queue()	        显示或操作匹配元素所执行函数的队列。
jQuery.queue()	    显示或操作匹配元素所执行函数的队列。
.removeData()	    移除之前存放的数据。
jQuery.removeData()	移除之前存放的数据。
-----------------------------------------------------------------------------
jQuery DOM 元素方法
函数	            描述
.get()	        获得由选择器指定的 DOM 元素。
.index()	    返回指定元素相对于其他指定元素的 index 位置。
.size()	        返回被 jQuery 选择器匹配的元素的数量。
.toArray()	    以数组的形式返回 jQuery 选择器匹配的元素。
-----------------------------------------------------------------------------
jQuery 核心函数
函数	                    描述
jQuery()	        接受一个字符串，其中包含了用于匹配元素集合的 CSS 选择器。
jQuery.noConflict()	运行这个函数将变量 $ 的控制权让渡给第一个实现它的那个库。
-----------------------------------------------------------------------------
jQuery 属性
下面列出的这些方法设置或返回元素的 CSS 相关属性。
属性	                    描述
context	            在版本 1.10 中被弃用。包含传递给 jQuery() 的原始上下文。
jquery	            包含 jQuery 版本号。
jQuery.fx.interval	改变以毫秒计的动画速率。
jQuery.fx.off	    全局禁用/启用所有动画。
jQuery.support	    表示不同浏览器特性或漏洞的属性集合（用于 jQuery 内部使用）。
length	            包含 jQuery 对象中的元素数目。
-----------------------------------------------------------------------------
