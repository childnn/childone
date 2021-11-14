package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 14:25
 */
public class GumballMachinePlusTest$ {
    public static void main(String[] args) {
        GumballMachinePlus gumballMachine = new GumballMachinePlus("Seattle", 112);
        GumballMonitor monitor = new GumballMonitor(gumballMachine);
        monitor.report();
    }
}
