package org.example.aop.aspectj;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/8/7
 */
public class MessageCommunicator {


    public MessageCommunicator() {
        System.out.println(getClass() + " init...");
    }

    public static void deliver(String message) {
        System.out.println(message);
    }

    public static void deliver(String person, String message) {
        System.out.println(person + ", " + message);
    }

}
