package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 15:00
 */
public class SoldOutState implements State {

    private GumballMachinePlus gumballMachine;

    public SoldOutState(GumballMachinePlus gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("Sold out...");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Sold out...");
    }

    @Override
    public void turnCrank() {
        System.out.println("Sold out...");
    }

    @Override
    public void dispense() {
        System.out.println("Sold out...");
    }
}
