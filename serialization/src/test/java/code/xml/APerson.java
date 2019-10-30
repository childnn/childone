package code.xml;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 16:46
 */
public class APerson {

    private String first, last;

    public APerson(String first, String last) {
        this.first = first;
        this.last = last;
    }

    // Constructor restores a APerson from XML:
    public APerson(Element person) {
        first = person.getFirstChildElement("first").getValue();
        last = person.getFirstChildElement("last").getValue();
    }

    // Make it human-readable:
    public static void format(OutputStream os, Document doc) throws Exception {
        Serializer serializer = new Serializer(os, "ISO-8859-1");
        serializer.setIndent(4);
        serializer.setMaxLength(60);
        serializer.write(doc);
        serializer.flush();
    }

    public static void main(String[] args) throws Exception {
        List<APerson> people = Arrays.asList(
                new APerson("Dr. Bunsen", "Honeydew"),
                new APerson("Gonzo", "The Great"),
                new APerson("Phillip J.", "Fry"));
        System.out.println(people);
        Element root = new Element("people");
        for (APerson p : people)
            root.appendChild(p.getXML());
        Document doc = new Document(root);
        format(System.out, doc);
        format(new BufferedOutputStream(new FileOutputStream("People.xml")), doc);
    }

    // Produce an XML Element from this APerson object:
    public Element getXML() {
        Element person = new Element("person");
        Element firstName = new Element("first");
        firstName.appendChild(first);
        Element lastName = new Element("last");
        lastName.appendChild(last);
        person.appendChild(firstName);
        person.appendChild(lastName);
        return person;
    }

    @Override
    public String toString() {
        return first + " " + last;
    }
}
