package org.anonymous.compound.composite;

import org.anonymous.compound.Quackable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 21:33
 */
public class Flock implements Quackable {
    private List<Quackable> quackers = new ArrayList<>();
    public void add(Quackable quacker) {
        quackers.add(quacker);
    }
    @Override
    public void quack() {
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            Quackable quacker = iterator.next();
            quacker.quack();
        }
    }
}
