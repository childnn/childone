package code.xml;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Elements;

import java.io.File;
import java.util.ArrayList;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 16:50
 */
public class People extends ArrayList<APerson> {

    public People(String fileName) throws Exception {
        Document doc = new Builder().build(new File(fileName));
        Elements elements = doc.getRootElement().getChildElements();
        for (int i = 0; i < elements.size(); i++)
            add(new APerson(elements.get(i)));
    }

    public static void main(String[] args) throws Exception {
        People p = new People("People.xml");
        System.out.println(p);
    }

}
