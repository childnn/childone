package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 15:03
 * Context(上下文) 是一个类, 它可以拥有一些内部状态. 在我们的例子中, GumballMachinePlus 就是这个 Context.
 * 不管是什么时候, 只要有人调用共 Context 的方法, 它就会被委托到状态来处理.
 * State 接口定义了一个所有具体状态的共同接口, 任何状态都实现这个相同的接口.
 *    这样一来, 状态之间可以相互替换.
 *
 */
public class GumballMachinePlus {
    // 一个实例变量现在持有一个状态对象, 而不是一个整数.
    private State soldOutState;
    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;
    /* 加进一个新的 WinnerState 状态, 然后在构造器中初始化. */
    private State winnerState;
    private int count;
    private State state = soldOutState;

    // 位置记录
    private String location;

    public GumballMachinePlus(String location, int count) {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);

        this.count = count;
        this.location = location;
        if (count > 0) {
            state = noQuarterState;
        }
    }

    public String getLocation() {
        return location;
    }

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }


    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (count != 0) {
            count--;
        }
    }

    // 重填.
    public void refill(int count) {
        this.count = count;
        state = noQuarterState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public int getCount() {
        return count;
    }

    public State getState() {
        return state;
    }

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
