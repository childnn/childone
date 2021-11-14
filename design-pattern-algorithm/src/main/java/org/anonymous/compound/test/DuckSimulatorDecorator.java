package org.anonymous.compound.test;

import org.anonymous.compound.*;
import org.anonymous.compound.adapter.GooseAdapter;
import org.anonymous.compound.decorate.QuackCounter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 15:43
 */
public class DuckSimulatorDecorator {
    public static void main(String[] args) {
        DuckSimulatorDecorator simulator = new DuckSimulatorDecorator();
        simulator.simulate();
    }

    private void simulate() {
        Quackable mallardDuck = new QuackCounter(new MallardDuck());
        Quackable redheadDuck = new QuackCounter(new RedheadDuck());
        Quackable duckCall = new QuackCounter(new DuckCall());
        Quackable rubberDuck = new QuackCounter(new RubberDuck());
        // 不记录 鹅叫声, 所以不记录鹅.
        Quackable gooseDuck = new GooseAdapter(new Goose()); // e.

        System.out.println("\nDuck Simulator");

        simulate(mallardDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);

        System.out.println("The ducks quacked " + QuackCounter.getQuacks() + " times");
    }

    private void simulate(Quackable duck) {
        duck.quack();
    }
}
