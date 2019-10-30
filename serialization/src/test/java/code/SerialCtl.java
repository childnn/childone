package code;

import java.io.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 15:05
 * 如果不是特别坚持实现 Externalizable 接口，那么还有另一种方法。我们可以实现 Serializable 接口，
 * 并添加（注意我说的是“添加”，而非“覆盖”或者“实现”）名为 writeObject() 和 readObject() 的方法。
 * 这样一旦对象被序列化或者被反序列化还原，就会自动地分别调用这两个方法。
 * 也就是说，只要我们提供了这两个方法，就会使用它们而不是默认的序列化机制。
 * ---
 * 在这个例子中，有一个 String 字段是普通字段，而另一个是 transient 字段，用来证明非 transient 字段
 * 由 defaultWriteObject() 方法保存，而 transient 字段必须在程序中明确保存和恢复。
 * 字段是在构造器内部而不是在定义处进行初始化的，以此可以证实它们在反序列化还原期间没有被一些自动化机制初始化。
 * ---
 * 如果我们打算使用默认机制写入对象的非 transient 部分，那么必须调用 defaultwriteObject() 作为
 * writeObject() 中的第一个操作，并让 defaultReadObject() 作为 readObject() 中的第一个操作。
 */
public class SerialCtl implements Serializable {
    private String a;
    private transient String b;

    public SerialCtl(String aa, String bb) {
        a = "Not Transient: " + aa;
        b = "Transient: " + bb;
    }

    public static void main(String[] args) {
        SerialCtl sc = new SerialCtl("Test1", "Test2");
        System.out.println("Before:\n" + sc);
        try (
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                ObjectOutputStream o = new ObjectOutputStream(buf)
        ) {
            o.writeObject(sc);
            // Now get it back:
            try (
                    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()))
            ) {
                SerialCtl sc2 = (SerialCtl) in.readObject();
                System.out.println("After:\n" + sc2);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return a + "\n" + b;
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(b);
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        b = (String) stream.readObject();
    }
}
