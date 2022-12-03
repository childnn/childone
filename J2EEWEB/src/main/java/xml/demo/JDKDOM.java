package xml.demo;

import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author child
 * 2019/5/7 18:06
 */
public class JDKDOM {

    static final String FILE_PATH = "src/main/resources/dom4j.xml";

    /**
     * jdk 解析 xml
     */
    //基本使用
    @Test
    public void test() throws ParserConfigurationException, IOException, SAXException {
        //SAXParserFactory.newInstance().newSAXParser().getXMLReader().parse();
        //首先得到 documentBuilder 对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // documentBuilder.parse(..): 参数可以传递 File, URL, InputStream
        //D:\Develop\IDEA_Project\J2EE\Day22TomcatAndServlet\src\dom4j.xml
        //相对路径以 当前模块为根路径(以 src 开始)
        Document document = builder.parse(new File(FILE_PATH));
        //启动文档分析
        Element root = document.getDocumentElement(); //根元素
        System.out.println(root); //[persons: null]
        System.out.println(root.getClass()); //class com.sun.org.apache.xerces.internal.dom.DeferredElementImpl
        String rootTagName = root.getTagName();
        System.out.println(rootTagName); //根元素名称
        System.out.println("==================");
        //获取子元素
        NodeList childNodes = root.getChildNodes();
        System.out.println(childNodes); //[persons: null]
        System.out.println(childNodes.getClass()); //class com.sun.org.apache.xerces.internal.dom.DeferredElementImpl
        int length = childNodes.getLength();
        System.out.println(length); //5
        //遍历
        /**
         * [#text:
         *     ]
         * [person: null]
         * [#text:
         *     ]
         * [person: null]
         * [#text:
         * ]
         */
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            System.out.println(item);
        }

        System.out.println("=====================");

        /**
         * 上面的循环会 计算 元素和元素之间的空格为一个 子元素
         * 下面的循环: 判断 得到的 item 是否是 Element 的子类
         * 如果是子类, 在进行接下来的操作: 这里得到结果 -- 两个 <person/> 子标签
         * [person: null]
         * [person: null]
         */
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                Element child = (Element) item;
                System.out.println(child);
            }
        }
    }

    @Test //获取子节点的文本内容
    public void test1() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(FILE_PATH));
        //得到根标签: persons
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            //得到 person 标签
            if (item instanceof Element) {
                Element child = (Element) item;

                //获取 person 标签的子标签
                NodeList grandItems = child.getChildNodes();
                //定义变量: 接收 文本信息
                //                String name = null;
                //                int age = 0;
                //                String sex = null;

                int length1 = grandItems.getLength();
                for (int i1 = 0; i1 < length1; i1++) {
                    Node item1 = grandItems.item(i1);
                    if (item1 instanceof Element) {
                        //得到 person 标签的子标签:  name/age/sex
                        Element grandson = (Element) item1;
                        //获取三个标签名
                        String tagName = grandson.getTagName();
                        //                        System.out.println(tagName);
                        //得到 三个 子标签的 文本信息
                        Text textNode = (Text) grandson.getFirstChild();
                        String text = textNode.getData().trim();  //以防万一: trim 一下

                        if ("name".equals(tagName)) {
                            System.out.print(text + ":");
                        } else if ("age".equals(tagName)) {
                            //字符串文本转 int
                            System.out.println(text);
                        } else if ("sex".equals(tagName)) {
                            System.out.print(text + ":");
                        }
                    }
                }
            }
        }
    }

    @Test // 获取子元素的文本内容二
    public void test2() throws IOException, SAXException, ParserConfigurationException {
        //获取 DocumentBuilder 对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        //启动加载程序
        Document document = documentBuilder.parse(new File(FILE_PATH));
        //获取 根元素: persons
        Element root = document.getDocumentElement();
        //获取根元素下的子元素集合
        NodeList childNodes = root.getChildNodes();
        //遍历
        int length = childNodes.getLength();
        ArrayList<NodeList> nodeLists = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                Element child = (Element) item;
                //得到 person 标签的子标签: name/age/sex
                NodeList grandsonNodes = child.getChildNodes();
                //把 所有的 person 标签放入集合
                nodeLists.add(grandsonNodes);
            }
        }

        //遍历
        for (NodeList nodeList : nodeLists) {
            //得到 每个 person 元素下的 子元素集合
            int length1 = nodeList.getLength();
            for (int j = 0; j < length1; j++) {
                Node item = nodeList.item(j);
                if (item instanceof Element) {
                    Element child = (Element) item;
                    //获取 子元素 name/sex/age 的文本信息
                    String textContent = child.getFirstChild().getTextContent();
                    System.out.println(textContent);
                }
            }
        }
    }

    @Test //其他
    public void test3() throws ParserConfigurationException, IOException, SAXException {
        //获取 document 对象
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(FILE_PATH));
        //获取根元素
        Element root = document.getDocumentElement();
        //获取根元素下的 第一个子元素: 第一个 person
        Node person1 = root.getFirstChild(); //空格
        //获取 元素的兄弟元素: 第二个 person
        Node person2 = person1.getNextSibling();
        //        System.out.println(person2); // 真正的 person
        Node lastChild = person2.getLastChild();
        /**
         * [#text:
         *     ]
         */
        //        System.out.println(lastChild); //相当于 空格

        //获取节点下的子节点的 另一种方式
        for (Node child = person2.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                System.out.println(((Element) child).getTagName()); //name/sex/age
            }
        }
    }

    @Test
    public void test4() {

    }

}
