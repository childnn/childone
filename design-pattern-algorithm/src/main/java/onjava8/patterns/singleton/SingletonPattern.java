package onjava8.patterns.singleton;

interface Resource {
    int getValue();

    void setValue(int x);
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/22 11:29
 */
public class SingletonPattern {
    public static void main(String[] args) {
        Resource r = Singleton.getResource();
        System.out.println(r.getValue());
        Resource s2 = Singleton.getResource();
        s2.setValue(9);
        System.out.println(r.getValue());
        try {
            // 不能这么做，会发生：compile-time error（编译时错误）.
            // Singleton s3 = (Singleton)s2.clone();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/*
 * 由于这不是从Cloneable基类继承而且没有添加可克隆性，
 * 因此将其设置为final可防止通过继承添加可克隆性。
 * 这也实现了线程安全的延迟初始化：
 */
final class Singleton {

    public static Resource getResource() {
        return ResourceHolder.resource;
    }

    private static final class ResourceImpl implements Resource {
        private int i;

        private ResourceImpl(int i) {
            this.i = i;
        }

        public synchronized int getValue() {
            return i;
        }

        public synchronized void setValue(int x) {
            i = x;
        }
    }

    private static class ResourceHolder {
        private static Resource resource = new ResourceImpl(47);
    }

}