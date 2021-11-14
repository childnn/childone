package org.anonymous.state;

import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 15:00
 */
public class HasQuarterState implements State {
    /* 增加随机数, 产生 10% 赢的机会. */
    private Random randomWinner = new Random(System.currentTimeMillis());
    private GumballMachinePlus gumballMachine;

    public HasQuarterState(GumballMachinePlus gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You can't insert another quarter");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    @Override
    public void turnCrank() {
        System.out.println("You turned...");

        int winner = randomWinner.nextInt(10);
        /* 如果赢了, 而且有足够的糖果可让他一次得到两颗的话, 我们就进入 WinnerState 状态;
           否则, 就进入 SoldState 状态.
        *  */
        if (winner == 0 && gumballMachine.getCount() > 1) {
            gumballMachine.setState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }

    @Override
    public void dispense() {
        System.out.println("No gumball dispensed");
    }
}
