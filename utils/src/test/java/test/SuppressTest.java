package test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/25 16:02
 */
public class SuppressTest {

    static class CloneTest implements Cloneable {

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        protected Object clone() throws CloneNotSupportedException {
            // return super.clone();
            return null;
        }

    }

}

// @SuppressWarnings("ClassNameSameAsAncestorName")
// class CollectTest extends test.regex.CollectTest {
//
// }