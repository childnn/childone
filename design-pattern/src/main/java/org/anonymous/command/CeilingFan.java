package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 21:46
 * 想要正确的实现 undo, 就必须把吊扇以前的速度考虑进去.
 */
public class CeilingFan {
    public static final Integer HIGH = 3;
    public static final Integer MEDIUM = 2;
    public static final Integer LOW = 1;
    public static final Integer OFF = 0;
    private String location;
    private Integer speed;

    public CeilingFan(String location) {
        this.location = location;
        speed = OFF;
    }

    public void high() {
        speed = HIGH;
    }

    public void medium() {
        speed = MEDIUM;
    }

    public void low() {
        speed = LOW;
    }

    public void off() {
        speed = OFF;
    }

    public Integer getSpeed() {
        return speed;
    }
}
