package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 15:46
 */
public class Goose {
    public void honk() {
        System.out.println("Honk");
    }

    @Override
    public String toString() {
        return "Goose pretending to be a Duck just quacked.";
    }
}
