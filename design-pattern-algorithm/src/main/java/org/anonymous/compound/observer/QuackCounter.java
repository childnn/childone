package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 14:26
 */
public class QuackCounter implements Quackable {
    private static int numberOfQuacks;
    private Quackable duck;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    @Override
    public void registerObserver(Observer observer) {
        duck.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        duck.notifyObservers();
    }

    public static int getNumberOfQuacks() {
        return numberOfQuacks;
    }

    @Override
    public String toString() {
        return duck.toString();
    }
}
