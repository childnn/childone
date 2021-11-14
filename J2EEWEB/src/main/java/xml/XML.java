package xml;

/**
 * @author child
 * 2019/5/1 11:58
 *
 * 标记语言: 可以用标签为数据赋予意义的语言.
 * HTML 是用于编写网页的标记语言. XML 是用于定义任意标记语言的元语言.
 * 通常把用于定义新语言的语言称作元语言. 通过使用 XML 可以定义出各种各样的新语言.
 *
 *
 * <p>
 * sgml: standard generalized markup language 标准通用标记语言
 * xml 与 html 都是其衍生语言。
 * <p>
 * xml 语法,解析,约束
 * xml: extensible markup language: 可扩展标记语言 -- 标签可以随便写(但是必须有标签),但是语法比 html 严格
 * html: hyper text markup language: 超文本标记语言 -- 都是内置标签, 但是可以不写标签, 浏览器也可以解析(.html)
 * 作用: 作为项目的配置文件来使用 - 常用: .properties(k=v), .xml(层级结构)
 * 语法: (比 html 更严格)
 * 1. 必须要有标签 - html 可以没有
 * 2. 有开始标签就一定要有结束标签 - html 可以没有
 * 3. 标签严格区分大小写 - html 标签不区分大小写
 * 4. xml 标签中的属性必须用 引号括起来:不管是 string 还是 int
 * 5. xml 标签中声明的属性必须都有指定的值: 如 html 中可以写 checked="checked" 简写为 checked, 而 xml 不允许这样.
 * 组成:
 * 1. 声明: 声明 本文件是一个 xml 文件
 * <?xml version="1.0" encoding="UTF-8" ?>  -- 必须在第一行第一列
 * (声明结束之后,必须要有根标签)
 * 2. 标签元素: 自定义, 有开始,就必须要有结束
 * <标签>内容</标签>, <标签../>
 * 3. 属性元素: 标签中的属性, 属性值必须用 单/双引号包裹
 * 4. 注释: <!-- ctrl+shift+/ -->
 * 5. 转义字符: 空格 &nbsp;  < &lt;  > &gt;
 * 6. cdata区域: 当需要用到大量转义时,就可以使用 cdata 区域替代 转移字符
 * 只要放入 cdata 区域的内容,任何特殊符号都不需要转义了,都会原样输出
 * 格式: <![CDATA[内容]]>
 * 总结:
 * 满足以下条件的 xml 文件, 称为 格式良好的 xml 文件
 * 1. 满足上述语法要求
 * 2. 必须有且只有一个根标签
 * <p>
 * xml 解析: DOM 解析, SAX 解析, PULL 解析(android) 常见三种解析技术
 * 为什么要解析: 获取 xml 中内容
 * 解析技术: 两种
 * 1. DOM 解析: document object model -- 文档对象模型
 * 特点: 将整个 .xml 文件加载到 内存中, 形成一个 dom 树(从根标签到子标签的层级结构)
 * 优点: 层级结构, 可以快速做增删改查
 * 缺点: 如果文件过大,容易造成内存溢出(一般不会出现)
 * 2. SAX 解析: simple api for xml -- xml 简单 api
 * 特点: 逐行加载, 逐行解析. 在读入 xml 文档时,生成相应的事件
 * 优点: 不会造成内存溢出
 * 缺点: 只能做查询,做不了增删改 (加载一行,解析一行,就丢弃一行)
 * <p>
 * 解析工具:
 * 解析 xml 的工具包
 * 好处: 不需要自己一步一步去实现解析过程,因为市面上给我们提供了封装好的解析工具包
 * Dom4j: 比较简单的解析工具包  -- 底层: DOM 解析技术
 * JDom: 类似 Dom4j
 * Jsoup: 功能强大的 DOM 方式的 xml 解析工具包, 对 HTML 解析也更加方便(爬虫解析)
 * 使用 Dom4j 解析 xml 的过程: 导包: dom4j.jar
 * 1. 创建 解析器对象
 * SAXReader reader = new SAXReader()
 * 2. 加载 xml 文件: 得到 DOM 树(document 对象)
 * Document document = reader.read(getClass().getClassLoader().getResourceAsStream("dom4j.xml));
 * 3. 获取跟标签
 * Element rootElement = document.getRootElement();
 * 4. 获取根标签下的子标签
 * List<Element> elements = rootElement.elements();
 * 遍历循环,获取 子标签的属性值
 * String 属性值 = elements.attributeValue("标签的属性名"); //可选: 根据需要
 * 获取子标签的子子标签
 * List<Element> elements0 = element.elements();
 * 遍历循环,
 * 获取标签名: String name = element0.getName();
 * 获取文本内容: String text = element0.getText();
 * <p>
 * Xpath 方式解析 xml: 依赖于 Dom4j (可以认为是对 dom4j 工具包的升级) -- 所以也是基于 DOM 解析技术
 * 使用 Dom4j 工具包解析: 根据根标签的方式确实可以获取到 xml 的数据
 * 但是 如果子标签很多,获取起来过于繁琐,所以我们可以使用 xpath 的方式获取
 * xpath 的方式解析 xml, 基于 dom4j  -- 需要先用 dom4j 获取到 dom 树才能进行接下来的操作
 * 使用:
 * 导包: dom4j.jar, jaxen.jar
 * 特点: 按照 标签路径(层级结构) 查找
 * 方法:
 * Node node = selectSingleNode("/persons/person/name") -- 默认获取第一个
 * List<Element> list = selectNodes("/persons/person/name");  -- 获取所有指定标签元素
 * 标签路径的写法：
 * /    -- 从根标签开始   绝对路径
 * //   -- 指定任意标签   相对路径
 * @ -- 条件筛选   谓语  -- 标签名[@属性名='属性值']
 * eg: Node node = document.selectSingleNode("//person[@id='p2']/name"); -- 获取 id="p2" 的 person 标签下的 name 标签元素
 * <p>
 * xml 约束:
 * 作用:
 * 1. 限制 xml 可以出现哪些标签,不能出现哪些标签
 * 2.
 * 分类: dtd, schema
 * dtd 约束: 内部方式 外部方式(.dtd)   document type definition. 文档类型描述.
 * 内部方式:
 * 直接在 xml 文档中书写
 * 格式: <!DOCTYPE 根标签 [要约束的内容]>
 * schema: 只有外部方式(.xsd)
 * schema 和 dtd 区别
 * 1. dtd 有自己的特殊语法,其本身不是 xml 文档; schema 本身就是一个 xml 文档
 * 2. schema 可以更方便被 xml 解析器解析
 * 3. schema 提供了更多的数据类型
 * 4. schema 支持名称空间
 * 5. schema 扩展名 .xsd
 * <p>
 * 工厂模式
 * 动态代理
 */
public class XML {
}
