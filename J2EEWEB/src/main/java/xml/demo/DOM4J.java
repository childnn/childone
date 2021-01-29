package xml.demo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

/**
 * @author child
 * 2019/5/1 13:08
 */
public class DOM4J {

    @Test //dom4j 解析 xml 步骤流程
    public void test1() throws DocumentException {
        //创建一个解析器 来解析 xml 文件
        SAXReader reader = new SAXReader();
        //使用解析器 加载 xml 文件, 得到 DOM 树: document 对象
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream("dom4j.xml"));

        //获取根标签
        Element rootElement = document.getRootElement();

        //获取根标签下的子标签
        List<Element> elements = rootElement.elements();

        for (Element element : elements) { //person
            //获取每个子标签的属性值
            String value = element.attributeValue("id"); //得到 id 属性的 属性值
            //            System.out.println(value);
            //            System.out.println(element.getName()); //person

            //获取子标签的子子标签..
            List<Element> elements1 = element.elements();
            for (Element element1 : elements1) { //name, age, sex
                //获取标签名
                String name = element1.getName();
                //获取文本内容
                String text = element1.getText();
                System.out.println(name + ":" + text);
            }
        }
    }

    @Test
    public void test3() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream("dom4j.xml"));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        //        List<Element> elements1 = null;
        System.out.println(elements);
        /*outer:
        while (0 != elements.size()) { //集合长度不为 0; 说明下面还有子标签
            //获取每一个子标签下的子子标签
            for (Element element : elements) {
                elements = element.elements();
                continue outer;
            }
        }*/
        for (Element element : elements) {
            List<Element> elements1 = element.elements();

        }
        do {


        } while (elements.size() != 0);
        System.out.println(elements);
        //上面的循环结束,得到最底层的标签
        /*for (Element element : elements) {
            String name = element.getName();
            String text = element.getText();
            System.out.println(name + ":" + text);
        }*/
    }

    @Test //测试:如果标签元素下没有子标签,继续调用 elements() 方法,会得到什么
    public void test2() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream("test_dom4j.xml"));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        System.out.println(elements); //空集合
        /*for (Element element : elements) {
            System.out.println(element);
        }*/
    }
}
