package beans;

import pojo.User;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/04/05
 */
public class JavaBeansTest {

    public static void main(String[] args) {
        try (
                FileOutputStream fos = new FileOutputStream("user.xml");
                XMLEncoder xmlEncoder = new XMLEncoder(fos)
        ) {
            User user = User.builder().name("jack").age(18).build();

            xmlEncoder.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                FileInputStream fis = new FileInputStream("user.xml");
                XMLDecoder xmlEncoder = new XMLDecoder(fis)
        ) {
            Object o = xmlEncoder.readObject();
            System.out.println("o = " + o);
            System.out.println("o.getClass() = " + o.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

