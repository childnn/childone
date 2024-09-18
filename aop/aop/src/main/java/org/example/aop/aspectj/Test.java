package org.example.aop.aspectj;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @see org.aspectj.internal.lang.reflect.AjTypeImpl#getPerClause()
 * @see org.aspectj.lang.reflect.DeclareAnnotation.Kind
 * @see org.aspectj.lang.JoinPoint#METHOD_CALL
 * @since 2024/8/7
 */
public class Test {

    static {

    }

    public Test() {
        System.out.println(getClass() + " init...");
    }

    /*

     an aspect declaration looks very much like a class declaration:

      [access specification] aspect <AspectName>
            [extends class-or-aspect-name]
            [implements interface-list]
            [<association-specifier>(Pointcut)]  {
            ... aspect body
     }

     */

    public static void main(String[] args) {
        MessageCommunicator.deliver("Wanna learn AspectJ?");

        System.out.println("\r\nMessageCommunicator.deliver 一个参数方法 执行完毕\r\n");

        MessageCommunicator.deliver("Harry", "having fun?");
    }

}

