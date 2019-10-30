package code;

import java.io.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 12:34
 * Externalizable 对象在默认情况下不保存它们的任何字段，
 * 所以 transient 关键字只能和 Serializable 对象一起使用。
 */
public class Blip3 implements Externalizable {
    private int i;
    private String s; // No initialization

    // 必须要有 public 的 无参构造
    public Blip3() {
        System.out.println("Blip3 Constructor");
        // s, i not initialized
    }

    public Blip3(String x, int a) {
        System.out.println("Blip3(String x, int a)");
        s = x;
        i = a;
        // s & i initialized only in non-no-arg constructor.
    }

    public static void main(String[] args) {
        System.out.println("Constructing objects:");
        Blip3 b3 = new Blip3("A String ", 47);
        System.out.println(b3);
        try (
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Blip3.serialized"))
        ) {
            System.out.println("Saving object:");
            o.writeObject(b3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Now get it back:
        System.out.println("Recovering b3:");
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blip3.serialized"))
        ) {
            b3 = (Blip3) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(b3);
    }

    @Override
    public String toString() {
        return s + i;
    }

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {
        System.out.println("Blip3.writeExternal");
        // You must do this:
        out.writeObject(s);
        //out.writeInt(i); // 类似 transient(瞬时) 的效果, 不调用 writexxx(), 禁止序列化, 可以屏蔽一些敏感信息
    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        System.out.println("Blip3.readExternal");
        // You must do this:
        s = (String) in.readObject();
        //i = in.readInt();
    }

}
