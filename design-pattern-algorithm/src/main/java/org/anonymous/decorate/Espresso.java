package org.anonymous.decorate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 15:20
 */
public class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso"; // 浓缩咖啡.
    }
    @Override
    public double cost() {
        return 1.99D;
    }
}

class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return .89D;
    }
}

class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "DarkRoast Coffee";
    }

    @Override
    public double cost() {
        return 0.88D;
    }
}