package org.anonymous.lombok.sneaky;

import lombok.SneakyThrows;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 16:20
 */
public class SneakyThrowsDemo implements Cloneable {

    @SneakyThrows
    @Override
    protected Object clone()/* throws CloneNotSupportedException*/ {
        return super.clone();
    }

    /*编译:
    *    protected Object clone() {
            try {
                return super.clone();
            } catch (Throwable var2) {
                throw var2;
            }
         }
    * */

}
