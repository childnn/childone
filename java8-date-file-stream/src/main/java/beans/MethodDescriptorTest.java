package beans;

import org.junit.Test;

import java.beans.MethodDescriptor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/04/06
 */
public class MethodDescriptorTest {

    @Test
    public void md() throws NoSuchMethodException {
        MethodDescriptor md = new MethodDescriptor(MethodDescriptorTest.class.getMethod("md"));
        // md.setExpert();
    }

}
