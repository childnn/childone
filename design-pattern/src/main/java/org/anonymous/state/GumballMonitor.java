package org.anonymous.state;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 17:12
 */
public class GumballMonitor {
    private GumballMachinePlus machine;

    public GumballMonitor(GumballMachinePlus machine) {
        this.machine = machine;
    }

    // 打印报告: 位置, 库存, 机器状态打印出来.
    public void report() {
        System.out.println("Gumball Machine: " + machine.getLocation());
        System.out.println("Current inventory: " + machine.getCount() + " gumballs");
        System.out.println("Current state: " + machine.getState());
    }
}
