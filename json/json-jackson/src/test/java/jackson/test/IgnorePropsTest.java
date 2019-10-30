package jackson.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.pojo.serial.ignoreprop.Student;
import jackson.pojo.serial.ignoreprop.User;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:59
 */
public class IgnorePropsTest {
    @Test
    public void test() throws JsonProcessingException {
        Student student = new Student().setId(3).setName("jack").setSex("female");
        ObjectMapper om = new ObjectMapper();
        String stu = om.writeValueAsString(student);
        System.out.println("stu = " + stu);

        System.out.println("===============================");

        Student student1 = om.readValue(stu, Student.class);
        System.out.println("student1 = " + student1);

        System.out.println("================================");

        User user = new User().setStu(student).setName("rose");
        String u = om.writeValueAsString(user);
        System.out.println("u = " + u);

        System.out.println("===============================");

        User user1 = om.readValue(u, User.class);
        System.out.println("user1 = " + user1);

    }

    /**
     * @see JsonIgnoreProperties#ignoreUnknown() 必选设置位 true, 否则有对象没有的(或者被忽略的)属性, 就会报错
     * @throws JsonProcessingException
     */
    @Test
    public void test1() throws JsonProcessingException {
        String s = "{\"id\":3,\"name\":\"jack\",\"sex\":\"female\", \"gender\":\"额外的属性\"}";
        ObjectMapper om = new ObjectMapper();
        Student student = om.readValue(s, Student.class);
        System.out.println("student = " + student);
    }

}
