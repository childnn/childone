package org.anonymous.netty.memcached;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 19:05
 * 一个操作一个字节.
 */
public class Opcode {

    public static final byte GET = 0x00;
    public static final byte SET = 0x01;
    public static final byte DELETE = 0x04;

}
