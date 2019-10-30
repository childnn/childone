package exception;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 17:28
 */
public class AutoCloseableDetails {
    public static void main(String[] args) {
        // 退出 try 块会调用两个对象的 close() 方法，并以与创建顺序相反的顺序关闭它们。
        // 顺序很重要，因为在此配置中，Second 对象可能依赖于 First 对象，因此如果
        // First 在第 Second 关闭时已经关闭。 Second 的 close() 方法可能会尝试访问 First 中不再可用的某些功能。
        try (
                First f = new First();
                Second s = new Second()
        ) {
        }
    }
}

class Reporter implements AutoCloseable {
    String name = getClass().getSimpleName();

    Reporter() {
        System.out.println("Creating " + name);
    }

    public void close() {
        System.out.println("Closing " + name);
    }
}

class First extends Reporter {
}

class Second extends Reporter {
}