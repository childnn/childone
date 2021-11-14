package org.anonymous.strategy;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/28 16:28
 */
public interface WeaponBehavior {
    /**
     * 性为.
     */
    void useWeapon();
}

class SwordBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        // 实现用宝剑挥舞.
    }
}

class KnifeBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        // 匕首.
    }
}

class BowAndArrowBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        // 弓箭.
    }
}

class AxeBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        // 斧头.
    }
}
