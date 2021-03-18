package org.anonymous.netty.memcached;

import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 17:25
 * Memcached 二进制协议.
 *  一般协议有 24 字节用于请求和响应. 这个头可以分解为如下信息:
 *   Sample Memcached header byte structure
 *  Field               Byte offset     Value
 *  Magic                   0            0x80 用于请求 0x81 用于响应
 *  OpCode                  1            0x01...0x1A
 *  Key length              2, 3          1...32,767
 *  Extra length            4            0x00, x04, 或 0x08
 *  Data type               5            0x00
 *  Reserved                6 和 7       0x00
 *  Total body length       8-11         所有 body 的长度
 *  Opaque                  12-15        任何带符号的 32-bit 整数, 这个也包含在响应中, 因此更容易将请求映射到响应
 *  CAS                     16-23        数据版本检查
 *
 */
public class MemcachedRequest { // 这个类将会发送请求到 Memcached server

    private static final Random rand = new Random();
    // 2. 魔数, 可用来标记文件或协议的格式
    private final int magic = 0x80; // fixed so hard coded
    // 3. opCode, 反应了响应的操作已经创建了
    private final byte opCode; // the operation, eg: set or get
    // 4. 执行操作的 key
    private final String key; // the key to delete, get or set
    // 5. 使用的额外的 flag
    private final int flags = 0xdeadbeef; // random
    // 6. 表明到期事件
    private final int expires; // 0 = item never expires
    // 7. body
    private final String body; // if opCode is set, the value
    // 8. 请求的 id. 这个 id 将在响应中回显
    private final int id = rand.nextInt(); // Opaque
    // 9. compare-and-check 的值
    private final long cas = 0; // data version check...not used
    // 10. 如果由额外的使用, 将返回 true
    private final boolean hasExtras; // not all ops have extras

    public MemcachedRequest(byte opCode, String key, String value) {
        this.opCode = opCode;
        this.key = key;
        this.body = value == null ? "" : value;
        this.expires = 0;
        // only set command has extras in our example
        hasExtras = opCode == Opcode.SET;
    }

    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }

    public int magic() {
        return magic;
    }

    public byte opCode() {
        return opCode;
    }

    public String key() {
        return key;
    }

    public int flags() {
        return flags;
    }

    public int expires() {
        return expires;
    }

    public String body() {
        return body;
    }


    public int id() {
        return id;
    }

    public boolean hasExtras() {
        return hasExtras;
    }

    public long cas() {
        return cas;
    }
}
