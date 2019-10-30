package org.anonymous.compound.test;

import org.anonymous.compound.Goose;
import org.anonymous.compound.Quackable;
import org.anonymous.compound.adapter.GooseAdapter;
import org.anonymous.compound.composite.Flock;
import org.anonymous.compound.decorate.QuackCounter;
import org.anonymous.compound.factory.AbstractDuckFactory;
import org.anonymous.compound.factory.CountingDuckFactory;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 21:26
 */
public class DuckSimulatorFactoryCompositeTest {
    public static void main(String[] args) {
        DuckSimulatorFactoryCompositeTest simulator = new DuckSimulatorFactoryCompositeTest();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();
        simulator.simulate(duckFactory);
    }

    private void simulate(AbstractDuckFactory duckFactory) {
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();

        GooseAdapter gooseDuck = new GooseAdapter(new Goose());

        System.out.println("\nDuck Simulator: With Composite - Flocks");

        Flock flockOfDucks = new Flock(); // 主群.

        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(rubberDuck);
        flockOfDucks.add(gooseDuck);

        Flock flockOfMallards = new Flock(); // 绿头鸭群.

        Quackable mallardOne = duckFactory.createMallardDuck();
        Quackable mallardTwo = duckFactory.createMallardDuck();
        Quackable mallardThree = duckFactory.createMallardDuck();
        Quackable mallardFour = duckFactory.createMallardDuck();

        flockOfMallards.add(mallardOne);
        flockOfMallards.add(mallardTwo);
        flockOfMallards.add(mallardThree);
        flockOfMallards.add(mallardFour);

        flockOfDucks.add(flockOfMallards); // 绿头鸭群加入主群.

        System.out.println("\nDuck Simulator: Whole Flock Simulation");
        simulate(flockOfDucks);

        System.out.println("\nDuck Simulator: Mallard Flock Simulation");
        simulate(flockOfMallards);

        System.out.println("\nThe ducks quacked " + QuackCounter.getQuacks() + " times");
    }

    private void simulate(Quackable duck) {
        duck.quack();
    }
}
