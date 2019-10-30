package jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.pojo.serial.ignore.Student;
import jackson.pojo.serial.ignore.User;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see jackson.pojo.serial.ignore.Student
 * @see jackson.pojo.serial.ignore.User
 * @since 2019/10/22 17:58
 */
public class IgnoreTest {
    @Test
    public void test() throws JsonProcessingException {
        Student student = new Student()
                .setId(1)
                .setName("jack")
                .setSex("female");
        ObjectMapper om = new ObjectMapper();
        String stu = om.writeValueAsString(student);
        System.out.println("stu = " + stu); // id 被忽略.

        Student student1 = om.readValue(stu, Student.class);
        System.out.println("student1 = " + student1); // id 是默认值.

        User user = new User()
                .setStu(student)
                .setName("rose");
        String u = om.writeValueAsString(user); // stu id 被忽略.
        System.out.println("u = " + u);

        User user1 = om.readValue(u, User.class);
        System.out.println("user1 = " + user1);
    }
}
