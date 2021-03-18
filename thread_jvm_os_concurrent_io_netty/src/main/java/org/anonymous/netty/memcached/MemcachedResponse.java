package org.anonymous.netty.memcached;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/2 10:01
 * Memcached 服务器的响应
 */
public class MemcachedResponse {
    // 魔数
    private final byte magic;
    // 创建操作的响应
    private final byte opCode;
    // 数据类型: 基于二进制还是文本
    private byte dataType;
    // 响应状态: 成功,失败
    private final short status;
    // 唯一 id
    private final int id;
    // compare-and-set
    private final long cas;
    // 额外的 flag
    private final int flags;
    // 有效期
    private final int expires;
    // 响应创建的 key
    private final String key;
    // 实际数据
    private final String data;

    public MemcachedResponse(byte magic, byte opCode,byte dataType,
                             short status, int id, long cas, int flags,
                             int expires, String key, String data) {
        this.magic = magic;
        this.opCode = opCode;
        this.dataType = dataType;
        this.status = status;
        this.id = id;
        this.cas = cas;
        this.flags = flags;
        this.expires = expires;
        this.key = key;
        this.data = data;
    }

    public byte magic() {
        return magic;
    }

    public byte opCode() {
        return opCode;
    }

    public byte dataType() {
        return dataType;
    }

    public short status() {
        return status;
    }

    public int id() {
        return id;
    }

    public long cas() {
        return cas;
    }

    public int flags() {
        return flags;
    }

    public int expires() {
        return expires;
    }

    public String key() {
        return key;
    }

    public String data() {
        return data;
    }
}
