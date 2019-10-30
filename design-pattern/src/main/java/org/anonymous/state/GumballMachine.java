package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 14:03
 * 有糖果机的地方, 永远充满活力.
 */
public class GumballMachine {
    private final static int SOLD_OUT = 0; // 售罄.
    private final static int NO_QUARTER = 1; // 退回/没有 25 分钱.
    private final static int HAS_QUARTER = 2; // 投入 25 分钱.
    private final static int SOLD = 3; // 发放糖果.
    /* 该实例变量跟踪当前状态, 一开始被设置为 "糖果售罄". */
    private int state = SOLD_OUT;
    /* 第二个实例变量, 用来追踪机器内的糖果数目. */
    private int count = 0;

    /**
     * 构造器需要初始化糖果库存量当作参数.
     * 如果库存量不为零的话, 机器就会进入 "没有 25 分钱" 的状态,
     * 也就是说它等着别人投入 25 分钱. 如果糖果数目为 0 的话,
     * 机器就会保持在 "糖果售罄" 的状态.
     */
    public GumballMachine(int count) {
        this.count = count;
        if (count > 0) {
            state = NO_QUARTER;
        }
    }

    /* 投币. */
    public void insertQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("You can't insert another quarter...");
        } else if (state == NO_QUARTER) {
            state = HAS_QUARTER;
            System.out.println("You inserted a quarter..");
        } else if (state == SOLD_OUT) {
            System.out.println("You can't insert a quarter, the machine is sold out~");
        } else if (state == SOLD) {
            System.out.println("Please wait, we're already giving you a gumball");
        }
    }

    /* 退钱. */
    public void ejectQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("Quarter returned..");
            state = NO_QUARTER;
        } else if (state == NO_QUARTER) {
            System.out.println("You haven't inserted a quarter");
        } else if (state == SOLD) {
            System.out.println("Sorry, you already turned the crank.."); // 已经转动 曲柄, 不能再退钱了. 已经拿到了糖果.
        } else if (state == SOLD_OUT) {
            System.out.println("You can't eject, you haven't inserted a quarter yet");
        }
    }

    /* 转动曲柄. */
    public void turnCrank() {
        if (state == SOLD) {
            System.out.println("Turning twice doesn't get you another gumball!");
        } else if (state == NO_QUARTER) {
            System.out.println("You turned but there's no quarter");
        } else if (state == SOLD_OUT) {
            System.out.println("You turned, but there are no gumballs");
        } else if (state == HAS_QUARTER) {
            System.out.println("You turned...");
            state = SOLD; // 拿到糖果, 改变状态.
            dispense(); // 发放糖果.
        }
    }

    /* 发放糖果. */
    private void dispense() {
        if (state == SOLD) {
            System.out.println("A gumball comes rolling out the slot");
            count--;
            /* 我们在这里处理 "糖果售罄" 的情况. 如果这是最后一颗糖果,
               我们就将机器的状态设置为 "售罄"; 否则, 就回到 "没有 25 分钱" 状态. */
            if (count == 0) {
                System.out.println("Oops, out of gumballs!");
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
            /* 以下的状态都不该发生, 但如果顾客这么做了, 他们得到的是错误消息, 而不是得到糖果. */
        } else if (state == NO_QUARTER) {
            System.out.println("You need to pay first!");
        } else if (state == SOLD_OUT) {
            System.out.println("No gumball dispensed..");
        } else if (state == HAS_QUARTER) {
            System.out.println("No gumball dispensed...");
        }
    }

    //


    @Override
    public String toString() {
        String msg = "\nInventory: " + count + " gumballs\n";
        if (count > 0) {
            msg += "Machine is waiting for quarter\n";
        } else {
            msg += "Machine is sold out";
        }
        return msg;
    }
}
