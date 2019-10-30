package jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.pojo.serial.ignoretype.Student;
import jackson.pojo.serial.ignoretype.User;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:59
 */
public class IgnoreTypeTest {
    @Test
    public void test() throws JsonProcessingException {
        Student student = new Student().setId(3).setName("jack").setSex("female");
        ObjectMapper om = new ObjectMapper();
        String stu = om.writeValueAsString(student);
        System.out.println("stu = " + stu);

        System.out.println("========================");

        Student student1 = om.readValue(stu, Student.class);
        System.out.println("student1 = " + student1);

        System.out.println("========================");

        User user = new User().setStu(student).setName("rose");
        String u = om.writeValueAsString(user); // stu 被忽略.
        System.out.println("u = " + u);

        User user1 = om.readValue(u, User.class);
        System.out.println("user1 = " + user1);
    }
}
