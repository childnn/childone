package org.anonymous.strategy;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/28 16:27
 */
public class Character {
    WeaponBehavior weapon;
    void fight() {
        weapon.useWeapon();
    }

    void setWeapon(WeaponBehavior weapon) {
        this.weapon = weapon;
    }
}

class King extends Character {

}

class Queen extends Character {

}

class Knight extends Character {

}

class Troll extends Character {

}
