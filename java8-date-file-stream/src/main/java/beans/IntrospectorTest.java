package beans;

import pojo.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/04/05
 */
public class IntrospectorTest {

    public static void main(
            String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        User jack = User.builder().name("Jack").age(12).build();

        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().equals("name")) {
                Method wm = pd.getWriteMethod();
                Object rose = wm.invoke(jack, "Rose");
                System.out.println("rose.getClass() = " + rose);
                System.out.println("jack = " + jack);
                //
                Method readMethod = pd.getReadMethod();
                Object invoke = readMethod.invoke(jack);
                System.out.println("invoke = " + invoke);
            }

        }

    }


}
