package org.anonymous.lombok;

import lombok.Getter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:38
 */
public class LazyGetterDemo {

    public static void main(String[] args) {
        LazyGetterDemo demo = new LazyGetterDemo();
        String lazy = demo.getLazy();
        String notLazy = demo.getNotLazy();

        System.out.println(lazy);
        System.out.println(notLazy);
    }

    @Getter
    private final String notLazy = createValue("not lazy");

    @Getter(lazy = true)
    private final String lazy = createValue("lazy");

    private String createValue(String name) {
        System.out.println("createValue(" + name + ")");
        return null;
    }

    /*编译后文件
    *
    *    public String getNotLazy() {
            return this.notLazy;
         }

        public String getLazy() {
            Object value = this.lazy.get();
            if (value == null) {
                synchronized(this.lazy) {
                    value = this.lazy.get();
                    if (value == null) {
                        String actualValue = this.createValue("lazy");
                        value = actualValue == null ? this.lazy : actualValue;
                        this.lazy.set(value);
                    }
                }
            }

            return (String)((String)(value == this.lazy ? null : value));
        }
    *
    * */
}
