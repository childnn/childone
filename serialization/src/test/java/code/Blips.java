package code;

import java.io.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 12:27
 * 这与恢复一个 Serializable 对象不同。对于 Serializable 对象，对象完全以它存储的二进制位为基础来构造，而不调用构造器。
 * 而对于一个 Externalizable 对象，所有普通的默认构造器都会被调用（包括在字段定义时的初始化），然后调用 readExternal()
 * 必须注意这一点--所有默认的构造器都会被调用，才能使 Externalizable 对象产生正确的行为。
 */
public class Blips {

    public static void main(String[] args) {
        System.out.println("Constructing objects:");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2();
        try (
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Blips.serialized"))
        ) {
            System.out.println("Saving objects:");
            o.writeObject(b1);
            o.writeObject(b2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Now get them back:
        System.out.println("Recovering b1:");
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blips.serialized"))
        ) {
            b1 = (Blip1) in.readObject();
            // OOPS! Throws an exception:
            //System.out.println("Recovering b2:");
            //b2 = (Blip2) in.readObject(); // java.io.InvalidClassException: code.Blip2; no valid constructor
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

class Blip1 implements Externalizable {

    public Blip1() {
        System.out.println("Blip1 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {
        System.out.println("Blip1.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }

}

class Blip2 implements Externalizable {

    // 非 public 的构造无法反序列化
    Blip2() {
        System.out.println("Blip2 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {
        System.out.println("Blip2.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }

}