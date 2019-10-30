package org.anonymous.jquery;

/**
 * @author child
 * 2019/4/3 9:26
 *
 * JQuery ： 一个 JavaScript 框架
 *    write less, do more: 更少的代码，做更多的事
 * JQuery 对象 与 JS 对象的转换
 *    jquery-xxx.js 与 jquery-xxx.min.js
 *      jquery-xxx.js: 开发版本。 给程序员看，有良好的缩进和注释。 文件体积大
 *      jquery-xxx.min.js: 生产版本。 程序中引入使用，文件体积小，程序加载快
 *    基本使用：
 *      引入:
 *          <script scr="./js/jquery-3.3.1.min.js"></script>
 *      调用:
 *          <script>
 *              var div1 = $("#div1");  --> var div1 = document.getElementById("div1");
 *              alert(div1.html()); --> alert(div1.innerHTML);
 *          </script>
 *      js 和 jq 对象的方法不通用
 *      转换:
 *          jq 对象转 js 对象: jq对象[index] 或者 jq对象.get(index)
 *          js 对象转 jq 对象: $(js对象)
 *
 *  js:
 *  window.onload = function() {
 *      $("#id").click(function() {});
 *  }
 *  onload 只能定义一次: 后面的自动覆盖前面的
 *  $(function(){}); 可以定义多次
 *  jq:
 *  $(function() {
 *      $("#id").click(function() {
 *          //设置 id="div" 的标签 背景色 red, 字体 yellow
 *          $("#div").css({"background-color":"red", "color":"yellow"});
 *      });
 *  });
 *
 *  1.基本选择器
 *      1) 元素选择器: $("标签名")
 *      2) id 选择器: $("#id")
 *      3) 类选择器: $(".类属性值")
 *      4) 并集选择器(组合选择器): $("选择器1,选择器2");
 *      5) 所有元素: *
 *  2.层级选择器:
 *       1) 后代选择器: 选择器1 选择器2  -- 选择器1 下的所有 选择器2
 *       2) 子元素选择器: 选择器1 > 选择器2 -- 选择器1 下最近一层的 选择器2 (子类)
 *       3) 兄弟选择器: 选择器1 ~ 选择器2 -- 同级元素 eg: $("form ~ input"): 与 from 标签同级的 input 标签
 *  3.属性选择器:
 *      1) 属性名选择器: 元素名[属性名]  -- 元素下有 指定属性的元素
 *      2) 属性值选择器:
 *              元素名[属性名="属性值"] -- 元素下有指定属性,且属性有指定属性值的元素
 *                      eg: $("input[type='text']")
 *                          $("a[href^='http']") : href 属性值以 "http" 开头的 a 标签
 *                          $("a[href$='.com']") : href 属性值以 ".com" 结尾的 a 标签
 *                          $("div[title*='x']") : title 属性值 包含 x 的 div 标签
 *              元素名[属性名 != "属性值"] -- 除了 指定元素名下 属性名="属性值" 的标签以外的所有 元素 (没有指定属性也可以)
 *      3) 复合属性选择器: 条件同时成立
 *              元素名[属性名1="值"][属性名="值"]
 *                  eg: $("div[id][title*='x']") : 有 id 属性,且 title 属性值 包含 x 的 div 标签
 *                      $("div#intro .head") : id="intro" 且 class="head" 的 <div></div>
 *  4.过滤选择器: 冒号
 *      1) 首元素选择器: :first 获得选择的元素中的第一个元素   -- $("div:first") -- $("ul li:first")
 *      2) 尾元素选择器: :last 获得选择的元素中的最后一个元素 -- $("div:last")
 *      3) 非元素选择器: :not(selector) 不包括指定内容的元素 -- $("div:not(.class)") : class 属性值不为 指定值 的 div 标签
 *      4) 偶数选择器: :even 从 0 开始 -- $("div:even")
 *      5) 奇数选择器: :odd 从 0 开始 -- $("div:odd")
 *      6) 索引选择器: :eq(index) 指定索引 -- $("div:eq(1)") : 索引为 1 的div 标签
 *      7) 父索引选择器: :gt(index) 大于指定索引 -- $("tr:gt(1):odd") : 索引大于 1 且为奇数的标签
 *      8) 子索引选择器: :lt(index) 小于指定索引
 *      9) 标题选择器: :header 获取标题(h1-h7) 元素, 固定写法 -- $(":header")
 *  5.表单过滤选择器: (属性选择器+过滤选择器)
 *      1) 可用元素选择器: :enabled 获取可用元素 -- $("input[type='text']:enabled").val("aaa") --- 改变可用文本输入框的 value 值
 *      2) 不可用元素选择器: :disabaled 获取不可用元素 -- $("input[type='text']:disabled").val("bbb") -- 改变不可用文本输入框的 value 值
 *      3) $("input[type='checkbox']:checked").length -- 获取被选中复选框的个数
 *          -- 选中表头 其他 全选: function f(obj) {$(".class").prop("checked", obj.checked);} -- js
 *                          function f(obj) {$(".class").prop("checked", $(obj).prop("checked"))} --jq
 *      4) $("#x > option:selected").length -- 获取多选下拉框的选中个数
 *            <select multiple="multiple" id="x">  -- 可多选的下拉框 -- shift 多选
 *                <option selected=""></option>
 *                <option></option>
 *                <option></option>
 *            </select>
 *
 * DOM 操作
 *      1.内容操作: 给方法设值,就是赋值,不给值就是获取返回值
 *          1) html(): 获取/设置 元素的标签体内容 (容器标签) <a>内容</a>  -- 包括标签中的子标签
 *          2) text(): 获取/设置 元素的标签体纯文本内容  -- 不包括子标签
 *          3) val(): 获取/设置 元素的 value 属性值 -- 自闭和标签(包括 textarea)
 *      2.属性操作:
 *          1) 通用属性操作
 *              attr(): 设置/获取
 *              removeAttr() : 删除
 *              prop()
 *              removeProp()
 *              attr 和 prop :
 *                  1.操作的是元素的固有属性: 使用 prop -- checked/selected 属性
 *                  2.操作元素的自定义属性: 使用 attr
 *          2) 对 class 属性操作
 *              addClass(class属性值): 给指定调用者设置指定的 class 属性值, 把指定类选择器的属性 设置给调用者 -- 适用于由 多个 css 样式设置(不方便使用css()方法设置样式时使用)
 *              removeClass(class属性值)
 *              toggleClass(class属性值): 切换 -- 如果存在指定的 class 属性值,则删除, 如果不存在,则添加 -- 相当于 在 addClass/removeClass 之间自动切换
 *           css(): 传一个参数,就是获取 指定 css 样式. 传两个参数,就是设置 css 样式
 *                  -- 获取: var = css("background-color"), 获取背景色
 *                  -- 第一个是属性名,第二个是属性值: eg: css("background-color","red"), 设置背景色
 *                  -- css({"background-color":"red", "color":"yellow"}); 同时设置背景色和字体色
 *       3. CRUD
 *          1) append(子元素): 将子元素追加到调用者(父元素)之后 -- 子父节点关系
 *          2) prepend(子元素): 将子元素添加到调用者最前方
 *
 *          3) appendTo(目标元素): 将调用者追加到指定目标元素(父元素)之后
 *          4) prependTo(目标元素): 将调用者加到指定目标元素(父元素)最前方
 *
 *          5) before(元素): 将指定元素添加到调用者之前 -- 同级
 *          6) after(元素): 将指定元素添加到调用者之后 -- 同级
 *
 *          7) remove(): 删除调用元素 -- 包括调用元素和子元素
 *          8) empty(): 清空调用元素下的子元素 -- 不包括调用元素自己
 *          9) clone(): 返回调用者的 拷贝 -- 包含所有子元素及其属性
 *
 *    siblings() : 返回调用者的同辈元素
 *
 *  动画:
 *     1.三种方式显示和隐藏元素 -- 参数都相同, 都有默认值,都可以不指定确定的参数
 *        1) 默认显示和隐藏方式
 *            show([speed, [easing], [fn]])
 *             参数: speed: 动画速度 -- 三个预定义的值("slow", "normal", "fast") -- 可以设置毫秒值(不需要引号)
 *                   easing: 指定切换效果. 默认 "swing"(动画效果 先慢中间快后慢) 可以设置 "linear"(匀速)
 *                   fn: 动画结束之后执行的回调函数
 *           hide(speed, [easing], [fn])
 *           toggle(...)
 *        2) 淡入淡出
 *           fadeIn(...)
 *           fadeOut(...)
 *           fadeToggle(...)
 *       3) 上/下滑动
 *          slideDown(...)
 *          slideUp(...)
 *          slideToggle(...)
 * 遍历:
 *     jq 对象.each(callback)
 *     $.each(object, [callback]) : 第一个参数可以是 js 数组,可以是 jq 对象
 *     for..of: for(e of 数组)
 *
 * 类 js 事件的 jq 方法: 支持链式编程
 *      click(function(){})
 *      mouseover(...)
 *      mouseout(...)
 *      focus(...): 如果不传递 回调函数参数, 则在页面加载之后, 调用对象自动获取 焦点
 *      submit(...): 提交调用的表单对象
 *
 *      on("事件名", function() {})  <-->  off() 解除 on() 绑定的事件
 *      bind() <--> unbind()  等价于 on()/off()
 *
 *      toggle(function() {}, function() {}....) : 在多个函数之间切换
 *
 * 插件: 增强 jQuery 功能 -- 自定义方法
 *     实现方式:
 *      $.fn.extend({
 *          方法名:function(可选参数) {
 *
 *          }
 *      });
 *       --- 对象方法: 通过 jq 对象调用
 *      $.extend({
 *          方法名:function(可选参数) {
 *
 *          }
 *      });
 *       --- 全局方法: 通过 $.方法名 调用
 *
 * -------------------------------------------------------------------
 *
 *
 * AJAX: Asynchronous JavaScript And XML 异步的 JavaScript 和 xml
 *  在无需重新加载整个网页的情况下,可以部分更新网页内容
 *  通过在后台与服务器进行少量数据交换, Ajax 可以是网页实现异步更新. -- 传统网页如果需要更新内容,必须重载整个网页页面 (这意味着 之前的内容会被全部替换为新的内容--最简单的:不利于表单填写/提交)
 *  ajax : js提供的一门高级技术
 *      作用: 做浏览器和服务器的数据交互
 *      浏览器和服务器交互的方式: -- 目前学习的只有 Ajax 是同步方式,其他的都是异步方式
 *          超链接: <a href=""></a>
 *          表单提交: action=""
 *          js 方式:
 *             1) location.href=""
 *             2) ajax
 *          ps: 要想使用 ajax, 就一定要写 js/jq 代码 !!!
 *     ajax 特点:
 *          可以让页面不刷新不提交的与服务器进行数据交互,给用户的体验更好 -- 异步方式
 *          之前页面与服务器数据交互,必须提交加载整个页面  -- 同步方式
 *
 *   浏览器和服务器端数据交互的方式: 同步与异步的关系(面试)
 *      同步: 客户端发送请求到服务端,方服务器返回响应之前,页面都处于等待不能操作状态 (请求过程中,服务器处理数据过程中,服务器响应回来过程中,浏览器只能等待)
 *      异步: 客户端发送请求到服务器端,无论服务器是否返回响应,页面可以随意做其他事情
 *      不论是同步方式还是异步方式都是做浏览器和服务器数据交互的
 *      Ajax 异步运行原理(面试题)
 *           当浏览器发送请求给服务器的时候,先将请求给浏览器中的 ajax 引擎(XMLHttpRequest), Ajax 引擎会提交请求到服务器,
 *           在请求的过程中,服务器处理内容的过程中,以及服务器响应回来的过程中,等待的都是引擎对象,而浏览器页面无需等待可以做其他业务
 *           直到服务器将数据返回给 Ajax 引擎后, Ajax引擎再将数据给浏览器解析
 * Ajax 原始方式:
 *   浏览器: 产生数据, 通过 js 提供的一个对象-- XMLHttpRequest, 使用这个对象就可以将数据带给服务器
 *   服务器: 产生数据, 以 xml 方式给浏览器
 * 升级之后:
 *   浏览器:(不变) 产生数据,通过js 提供的对象 -- XMLHttpRequest, 使用这个对象就可以将数据带给服务器
 *   服务器: 产生数据,简单数据(字符串),可以直接以字符串格式返回
 *          复杂数据(对象,list,map..) 可以转换成 json 格式返回
 *      注: Ajax 接收服务器返回的数据: 接收不了对象 list,map.. 所以必须转换成 json 能够解析的
 *
 * js 的 ajax (了解)
 *      1.创建引擎对象(XMLHttpRequest 对象)
 *      2.编写回调函数
 *      3.确定请求方式和请求路径: 引擎对象.open(提交方式, 请求路径) -- xhr.open("get", "${pageContext.request.contextPath}/jas?username=aaaa");
 *      4.发送请求: 引擎对象.send();
 * js 的 Ajax api 概述
 *     XMLHttpRequest: 引擎对象
 *     onreadystatechange: 检测 引擎对象 的状态变化
 *          只要引擎对象状态变换一次,该函数就执行一次
 *       属性:
 *          readyState: 可以输出引擎对象的变化
 *              0: 刚创建
 *              1: 执行 open 方法
 *              2: 执行 send 方法
 *              3: 部分响应已生成
 *              4: 响应已生成(主要关注) --
 *          status: 响应的状态码(响应成功: 200) -- 针对 404 问题
 *          responseText: 响应回来的文本
 * jquery 的 ajax (重点-------------------------)
 *   方式一: $.get(url, [data], [fn], [type]) -- 发送一个 get 请求  -- jq_ajax/demo01.jsp
 *   方式二: $.post(url, [data], [fn], [type]) -- 发送一个 post 请求 -- jq_ajax/demo02.jsp
 *   参数:
 *      url:要访问的地址
 *      data: 要传递的数据 -- 2 种: key=value&key=value   json 格式方式: {key1:value1, key2:value2...}
 *      fn(d): 回调函数  d:代表服务器响应回来的数据  -- 该回调函数,只有响应返回,且响应成功才会执行(响应状态码status==200, 引擎对象状态为 readyState == 4)
 *      type: 把服务器响应回来的数据 转换成指定类型  默认(可以不写): text(字符串)  复杂数据: json 类型
 *   方式三: $.ajax({key:value,...}) -- 7 个属性
 *    参数:
 *      url: 访问地址
 *      data: 请求传递的参数
 *      sync: 是否异步提交 默认异步(true)
 *      dataType: 服务器返回来的数据类型 默认不写字符串
 *      type: 提交方式 --get/post
 *      success: 服务器响应成功之后的回调函数
 *      error: 服务器响应失败之后的回调函数
 * jQuery 3.0 还有 2 种 -- 签名方式 -- 签名 + 六种属性
 *   方式四: $.get({key:value,...})
 *   方式五: $.post({key:value,...})
 *   参数:
 *      url:访问地址
 *      data:请求传递参数
 *      sync:是否异步 默认异步(true)
 *      dataType:服务器返回的数据类型 默认 "text"  -- 可以设置: "json" -- 转换工具转换之后的返回形式(json 格式的字符串形式)
 *      success:服务器响应成功之后的回调函数
 *      error:服务器响应失败之后的回调函数
 *
 * json数据 -- 会获取 json 中的数据即可.  JavaScript object notation. js 对象简谱
 *  概述: json -- 一种数据的交换格式, 属于 JavaScript 的语法,能够被 ajax 快速解析
 *  json 的数据格式(三种)
 *      格式一: 对象格式 -- 对象.key 得到 value
 *          {key1:value1, key2:value2...} -- key 必须为 字符串类型, value 可以时任意类型
 *      格式二: 数组格式 -- 数组名[index]
 *          [value1, value2, value3...]  -- value 可以为任意类型
 *      格式三: 混合格式
 *          [{key1: value1, key2: value2}, {key1: value1, key2: value2}]
 *          {key1: [{key1: value1}, {key1: value1}]}
 *     注: 咱们再开发中不写 json 格式数据,但一定会解析 json 数据获取内容
 *     问题: 怎么将 对象/list/map 转换成 json 格式数据?
 *
 *  ajax --> servlet --> 数据库 --> 对象/list/map --(转换工具)--> 转换json 数据 --> 回调函数
 *
 *   json 转换工具: Jackson
 *      作用: 直接将 Java 对象/集合 转换成 json 格式的字符串
 *      使用步骤:
 *          1. 导 3 个包
 *          2.使用 api 转换
 *              ObjectMapper objectMapper = new ObjectMapper();
 *              方法: writeValueAsString(对象/list/map...);
 *              返回: json 格式的字符串 -- 不是json 原格式,是字符串格式
 *
 *  总结:
 *      ajax: js 提供的异步的浏览器和服务器的数据交换技术
 *      作用: 可以让页面不刷新,不提交 和 服务器进行数据交换
 *      异步: 浏览器和服务器交互中, 页面无需等待
 *      同步: 浏览器和服务器交互中, 页面需要等待
 *      不论异步还是同步,都是浏览器和服务器的交互技术
 *
 *      jq的 Ajax 使用方式:
 *          方式一: $.get(url, data, function(d){}, dataType);
 *          方式二: $.post(url, data, function(d){}, dataType);
 *          方式三: $.ajax({  -- 7 种属性
 *                      sync: true, //是否打开异步,默认 true
 *                      url: value,
 *                      type: "get"/"post", //提交方式: get/post
 *                      data: "key1=value1&key2=value2..",
 *                      dataType: "text"/"json", //说明是 普通字符串,还是 json 格式的字符串形式. 默认是 "text"
 *                      success: function(d){}   //回调函数 : 响应成功时执行
 *                      error: function() {} //响应错误时执行: 如 请求地址错误,会返回错误的响应
 *                  })
 *          jQuery 3.0 特有方式 -- 签名方式: 签名 + 6 种属性
 *          方式四: $.get({ -- 相当于把 方式三中的 type 属性值 写到方法名上
 *                      sync: true, //开启异步
 *                      url: value,
 *                      data: "key1=value1&key2=value2",
 *                      dataType: "text"/"json",
 *                      success: function (d) {}, //回调函数
 *                      error: function () {}
 *                  });
 *          方式五: $.post({
 *                      sync: true,
 *                      url: value,
 *                      data: "key1=value1&key2=value2",
 *                      dataType: "text"/"json",
 *                      success: function (d) {}, //回调函数
 *                      error: funtion() {}
 *                  });
 *  json 数据: js 提供的数据交换格式 -- 可以快速被 Ajax 解析
 *     格式: 3 种
 *       1. 对象格式  例如: {key1: value1, key2: value2...}   --- key 必须为 字符串. value 可为任意类型
 *       2. 数组格式  例如: [value1, value2...]  --  value 可以为任意类型
 *       3. 混合格式  例如: [{key1: value1...}, {key1: value1...}]  {key:[{key1: value1...}, {key1: value1...}]}
 *    ajax 回调函数接收不到 Java 复杂类型 例如: 对象/list/map
 *      必须使用 json 转化工具: Jackson  作用: 将 Java 的复杂数据转换成 json 格式的字符串形式
 */
public class JQueryDemo {
}
