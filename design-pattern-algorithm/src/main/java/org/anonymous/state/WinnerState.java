package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 16:47
 */
public class WinnerState implements State {
    private GumballMachinePlus gumballMachinePlus;

    public WinnerState(GumballMachinePlus gumballMachinePlus) {
        this.gumballMachinePlus = gumballMachinePlus;
    }

    @Override
    public void insertQuarter() {

    }

    @Override
    public void ejectQuarter() {

    }

    @Override
    public void turnCrank() {

    }

    @Override
    public void dispense() {
        System.out.println("YOU'RE A WINNER! You get two gumballs for your quarter");
        gumballMachinePlus.releaseBall();
        if (gumballMachinePlus.getCount() == 0) {
            gumballMachinePlus.setState(gumballMachinePlus.getSoldOutState());
        } else {
            gumballMachinePlus.releaseBall(); // 释放第二颗糖.
            if (gumballMachinePlus.getCount() > 0) {
                gumballMachinePlus.setState(gumballMachinePlus.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumballs!");
                gumballMachinePlus.setState(gumballMachinePlus.getSoldOutState());
            }
        }
    }
}
