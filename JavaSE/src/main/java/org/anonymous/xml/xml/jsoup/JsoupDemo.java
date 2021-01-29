package org.anonymous.xml.xml.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JsoupDemo {

    public static void main(String[] args) throws IOException {
        //获取
        String path = JsoupDemo.class.getClassLoader().getResource("org/anonymous/xml/xml_Constraint/dtd/student.xml").getPath();
        Document document = Jsoup.parse(new File(path), "utf-8");
        System.out.println(document); //dom 树
        Elements elements = document.getElementsByTag("Student");
        //        System.out.println(elements.size());
        Element element = elements.get(0);
        String name = element.text();
        //        System.out.println(name);

    }

    private static String getDom() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "\n" +
                "<students>\n" +
                "\t<Student number=\"itcast_0001\">\n" +
                "\t\t<name>tom</name>\n" +
                "\t\t<age>18</age>\n" +
                "\t\t<sex>male</sex>\n" +
                "\t</Student>\n" +
                "\t\n" +
                "</students>";
    }

    @Test //方法二： 直接传入 xml 文件内容 -- String
    public void test() {
        Document document = Jsoup.parse(getDom());
        System.out.println(document);
    }
}
