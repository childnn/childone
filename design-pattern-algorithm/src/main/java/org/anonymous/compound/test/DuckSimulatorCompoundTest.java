package org.anonymous.compound.test;

import org.anonymous.compound.observer.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 14:33
 */
public class DuckSimulatorCompoundTest {
    public static void main(String[] args) {
        DuckSimulatorCompoundTest simulator = new DuckSimulatorCompoundTest();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();

        simulator.simulator(duckFactory);
    }

    private void simulator(AbstractDuckFactory duckFactory) {
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();

        GooseAdapter gooseDuck = new GooseAdapter(new Goose());

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

        System.out.println("\nDuck Simulator: With Observer");

        Quackologist quackologist = new Quackologist();
        flockOfDucks.registerObserver(quackologist);

        simulator(flockOfDucks);

        System.out.println("\nThe ducks quacked " + QuackCounter.getNumberOfQuacks() + " times");
    }

    private void simulator(Quackable duck) {
        duck.quack();
    }

}
