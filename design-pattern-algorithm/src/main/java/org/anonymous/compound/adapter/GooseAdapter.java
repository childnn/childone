package org.anonymous.compound.adapter;

import org.anonymous.compound.Goose;
import org.anonymous.compound.Quackable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 15:47
 * 适配器模式.
 */
public class GooseAdapter implements Quackable { // 使用 适配器, 将鹅适配成鸭子..
    private Goose goose;

    public GooseAdapter(Goose goose) {
        this.goose = goose;
    }

    @Override
    public void quack() {
        goose.honk();
    }
}
