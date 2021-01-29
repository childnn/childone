package xml.demo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

/**
 * @author child
 * 2019/5/1 13:49
 */
public class Xpath {

    @Test //获取单个
    public void test1() throws DocumentException {
        //获取 dom 树
        SAXReader reader = new SAXReader();
        Document document = reader.read(Xpath.class.getClassLoader().getResourceAsStream("dom4j.xml"));
        //使用 xpath api: 根据标签路径获取标签
        //        Node node = document.selectSingleNode("/persons/person/name"); //绝对路径方式: 默认获取第一个
        //        Node node = document.selectSingleNode("//name"); //相对路径: 默认获取第一个
        Node node = document.selectSingleNode("//person[@id='p2']/name"); //条件筛选，指定 获取哪一个
        String text = node.getText();
        System.out.println(text);
    }

    @Test //获取多个
    public void test2() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream("dom4j.xml"));
        //        List<Element> nodes = document.selectNodes("/persons/person/name"); //绝对路径
        List<Node> nodes = document.selectNodes("//name");
        for (Node element : nodes) {
            String name = element.getName();
            String text = element.getText();
            System.out.println(name + ":" + text);
        }
    }

}
