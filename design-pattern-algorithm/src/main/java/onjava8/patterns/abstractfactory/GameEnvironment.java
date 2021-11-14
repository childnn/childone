package onjava8.patterns.abstractfactory;

import java.util.function.Supplier;

interface Obstacle {
    void action();
}

interface Player {
    void interactWith(Obstacle o);
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 18:51
 * 抽象工厂模式看起来像我们之前所见的工厂对象，但拥有不是一个工厂方法而是几个工厂方法，
 * 每个工厂方法都会创建不同种类的对象。 这个想法是在创建工厂对象时，你决定如何使用该工厂创建的所有对象。
 * 《设计模式》中提供的示例实现了跨各种图形用户界面（GUI）的可移植性：你创建一个适合你正在使用的GUI的工厂对象，
 * 然后从中请求菜单，按钮，滑块等等，它将自动为GUI创建适合该项目版本的组件。
 * 因此，你可以将从一个GUI更改为另一个所产生的影响隔离限制在一处。
 */
public class GameEnvironment {

    private Player p;
    private Obstacle ob;

    public GameEnvironment(GameElementFactory factory) {
        p = factory.player.get();
        ob = factory.obstacle.get();
    }

    public static void main(String[] args) {
        GameElementFactory kp = new KittiesAndPuzzles(), kd = new KillAndDismember();
        GameEnvironment g1 = new GameEnvironment(kp), g2 = new GameEnvironment(kd);
        g1.play();
        g2.play();
    }

    public void play() {
        p.interactWith(ob);
    }

}

class Kitty implements Player {

    @Override
    public void interactWith(Obstacle ob) {
        System.out.print("Kitty has encountered a ");
        ob.action();
    }

}

class KungFuGuy implements Player {

    @Override
    public void interactWith(Obstacle ob) {
        System.out.print("KungFuGuy now battles a ");
        ob.action();
    }

}

class Puzzle implements Obstacle {

    @Override
    public void action() {
        System.out.println("Puzzle");
    }

}

class NastyWeapon implements Obstacle {

    @Override
    public void action() {
        System.out.println("NastyWeapon");
    }

}

// The Abstract Factory:
class GameElementFactory {
    Supplier<Player> player;
    Supplier<Obstacle> obstacle;
}

// Concrete factories:
class KittiesAndPuzzles extends GameElementFactory {
    KittiesAndPuzzles() {
        player = Kitty::new;
        obstacle = Puzzle::new;
    }
}

class KillAndDismember extends GameElementFactory {
    KillAndDismember() {
        player = KungFuGuy::new;
        obstacle = NastyWeapon::new;
    }
}