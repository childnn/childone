package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jackson.pojo.serial.Student;
import jackson.pojo.serial.User;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 16:51
 */
public class Demo {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Student stu = new Student().setId(1).setName("jack").setSex("male");
        String stus = om.writeValueAsString(stu);
        System.out.println("stus = " + stus);
        User user = new User().setName("rose").setStu(stu);
        String users = om.writeValueAsString(user);
        System.out.println("users = " + users);

        User user1 = om.readValue(users, User.class);
        System.out.println("user1 = " + user1);
    }

    @Test
    public void test1() {
        ObjectMapper om = new ObjectMapper();
        // om.
    }
}
