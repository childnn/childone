package org.anonymous.xml.xml;

/**
 * shadow
 * 2019/3/20 17:55
 * w3c: world wide web consortium. 万维网联盟
 * xml : extensible markup language. 可扩展标记语言 : 标签自定义
 * HTML and xml:
 * xml 是 HTML 的扩展,比 HTML 语法严格
 * HTML 用来展示数据, xml 用来存储数据
 * HTML 标签预定义, xml 标签自定义
 * xml 基本语法:
 * 1.xml 文档后缀名 .xml
 * 2.xml 第一行必须定义为文档声明: <?xml version="1.0" encoding="UTF-8"?> (必须为数字是 1 的行,不能其他行)
 * 3.xml 文档有且只有一个根标签
 * 4.属性值必须使用引号(单双都可)
 * 5.标签必须正确关闭
 * 6.严格区分大小写
 * 组成部分:
 * 1.文档声明: <?xml version="1.0" encoding="UTF-8"?> // <?xml 属性列表 ?>
 * 属性列表:
 * 1) version: 版本号, 必须写
 * 2) encoding: 编码方式. 告知解析引擎当前文档使用的字符集,默认值 : ISO-8859-1
 * 3) standalone: 是否独立. 取值: yes: 不依赖其他文件, no: 依赖其他文件
 * 2. 指令(了解): 结合 css
 * 有 css 样式, 就以 HTML 的方式 展示数据
 * 没有 css 样式, 就以 存储数据的形式显示包括标签在内的所有内容
 * 3. 标签: 名称自定义
 * 命名规则: 名称可以包含字母、数字以及其他的字符
 * 名称不能以数字或者标点符号开始
 * 名称不能以字母 xml（或者 XML、Xml 等等）开始
 * 名称不能包含空格
 * 4. 属性:
 * id 属性值 唯一.
 * 5. 文本:
 * 转义字符:
 * CDATA 区: 数据原样展示, 替代转义字符: <![CDATA[需要展示的数据]]>
 * 6. 约束: -- 定义 xml 文档书写规则(元素及其属性的定义规则)
 * 元素的类别,元素出现的次数,顺序. 属性值(的类型)
 * 分类:
 * DTD: 简单的约束技术
 * 引入 dtd 文档到 xml 中
 * 内部 dtd: 将约束规则定义在 xml 文档中 : <!DOCTYPE 根元素 [元素声明]>
 * 外部 dtd: 将约束规则定义在外部的 dtd 文件中   -------- 如果使用了外部引入方式，不能再使用内部方式
 * 1) 本地: <!DOCTYPE 根标签 SYSTEM "dtd 文件路径名称">
 * 2) 网络: <!DOCTYPE 根标签 PUBLIC "dtd 文件名称（别名）" "文件在网络上的地址" >   ------ 以后的引入都以框架的官网引入
 * 约束的格式--     元素声明:  元素名称, 内容, 属性
 * 1. 对元素的约束:
 * 元素类别, 出现次数, 顺序
 * 子元素出现的次数: ? (0或1次), + (1或多次), * (0或多次)
 * 子元素出现的顺序:
 * 关系符号 或 |  --> 可以不按顺序
 * 逗号: ,  --> 必须按顺序
 * 复杂元素: 元素下面有 子元素
 * <!ELEMENT 元素名称 (子元素名称 1,子元素名称 2,.....)>  --> 父元素下 [必须] 有小括号中限定的 [全部] 子元素, 且要按指定 顺序
 * 只出现一次的 子元素: <!ELEMENT 元素名称 (子元素名称)>
 * 出现 1 次或多次(最少出现一次)的元素:  <!ELEMENT 元素名称 (子元素名称+)>
 * 出现 0 次或多次(最少 0 次)的元素: <!ELEMENT 元素名称 (子元素名称*)>
 * 出现 0 次或 1 次(只能是 0 或 1): <!ELEMENT 元素名称 (子元素名称?)>
 * <!ELEMENT person (name|age|sex) >   --> person 元素(标签)下 只能出现 name,age,sex 中的任意一个元素(标签), 且只能出现一次
 * <!ELEMENT person (name|age|sex)* >   --> person 元素(标签)下 只能出现 name,age,sex 中的任意元素(标签), 可以出现 0 或多次, 如果有 不同元素, 不区分出现的顺序
 * 注： 星号/加号/问号 放在 括号里面，只对星号所修饰的元素有效， 放在括号外面，对整个括号中的元素有效
 * 混合声明: <!ELEMENT note (#PCDATA|to|from|header|message)*>  --> ："note" 元素可包含出现零次或多次的 PCDATA、"to"、"from"、"header" 或者 "message"。
 * 简单元素: 元素下面只有文本内容
 * 空元素: <!ELEMENT 元素名称 EMPTY> : 自闭和标签,没有 可以被解析的文本内容
 * eg. <!ELEMENT br EMPTY> -->  <br />
 * PCDATA 元素: 字符串 <!ELEMENT 元素名称 (#PCDATA)>
 * eg. <!ELEMENT from (#PCDATA)>
 * ANY 元素: 任何类型数据
 * eg. <!ELEMENT 元素名称 ANY>
 * 2. 对元素属性的约束:
 * #REQUIRED 属性值是必需的
 * #IMPLIED 属性不是必需的
 * #FIXED value 属性值是固定的
 * 格式： <!ATTLIST 元素名称 属性名称 属性类型 默认值>
 * eg: <!ATTLIST person id CDATA #REQUIRED>  -- person 标签必须要有 id 属性
 * <p>
 * Schema: 复杂的约束技术(严格的约束)
 * 1. 填写 xml 文档的根元素
 * 2. 引入 sxi 前缀. xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * 3. 引入xsd文件命名空间.  xsi:schemaLocation="http://www.itcast.cn/xml  student.xsd"
 * <p>
 * 重点: xml 的解析 (增删改 用的少)
 * 分类:
 * 1.DOM : 将标记语言文档一次性加载进内存, 在内存中形成一颗 dom 树
 * 优点: 操作方便, 可以对文档进行 CRUD 的所有操作
 * 缺点: 站内存
 * 2.SAX : 逐行读取, 基于事件驱动的
 * 优点: 不占内存
 * 缺点: 只能读取, 不能曾删改
 * xml 常见解析器:
 * JAXP : sun 公司提供的解析器,支持 dom 和 sax 两种思想. -- 效率低,用的少
 * DOM4J: 一款优秀的解析器
 * Jsoup: HTML 解析器, 可以直接解析某个 URL 地址、HTML 文本内容。 它提供了一套非常省力的 API, 可以通过
 * DOM, CSS 以及类似于 jQuery 的操作方法来取出和操作数据
 * PULL： Android 操作系统内置解析器， sax 解析方式
 * Jsoup：
 * 1. 导包
 * 2. 获取 Document 对象
 * 3. 获取对应的表亲啊元素 Element 对象 -- 继承至 ArrayList 集合
 * 4. 获取数据 - 通过方法
 */
public class Demo {
}
