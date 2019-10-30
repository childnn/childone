package org.anonymous.compound.test;

import org.anonymous.compound.Goose;
import org.anonymous.compound.Quackable;
import org.anonymous.compound.adapter.GooseAdapter;
import org.anonymous.compound.decorate.QuackCounter;
import org.anonymous.compound.factory.AbstractDuckFactory;
import org.anonymous.compound.factory.CountingDuckFactory;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 21:26
 */
public class DuckSimulatorFactoryTest {
    public static void main(String[] args) {
        DuckSimulatorFactoryTest simulator = new DuckSimulatorFactoryTest();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();
        simulator.simulate(duckFactory);
    }

    private void simulate(AbstractDuckFactory duckFactory) {
        Quackable mallardDuck = duckFactory.createMallardDuck();
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();

        GooseAdapter gooseDuck = new GooseAdapter(new Goose());

        System.out.println("\nDuck Simulator: With Abstract Factory");

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
