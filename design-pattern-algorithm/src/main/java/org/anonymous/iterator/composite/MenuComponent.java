package org.anonymous.iterator.composite;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/9 16:45
 *
 * 关于 组件(component), 组合(composite), 树(leaf).
 *  -- 组合 包含 组件. 组件有两种: 组合 与 叶节点 元素.
 *  -- 组合 持有一群孩子, 这些孩子可以是别的 组合 或者 叶节点 元素.
 *
 *
 * 菜单组件 {@link MenuComponent} 提供了一个接口, 让 菜单项 {@link org.anonymous.iterator.MenuItem} 和 菜单 {@link org.anonymous.iterator.Menu} 共同使用.
 * 因为我们希望能够为这些方法提供默认的实现, 所以我们在这里使用一个抽象类.
 * 1. 女招待(waitress) 将使用菜单组件接口访问 菜单 和 菜单项.
 * 2. MenuItem 覆盖了对它有意义的方法, 至于那些没有意义的方法(eg: add()), 就置之不理.
 *    之所以没意义, 是因为菜单项已经是 叶节点, 它下面不能再有任何组件.
 * 3. Menu 也覆盖了一些对他有意义的方法, 例如增加或者删除菜单项(或者其他的菜单!).
 *    除此之外, 我们也使用 getName() 和 getDescription() 方法来返回菜单名称与描述.
 * ---
 * 所有组件必须实现 MenuComponent 接口; 然而, 叶节点和组合节点的角色不同, 所以有些方法可能并不适合
 * 某种节点. 面对这种情况, 有时候, 最好抛出运行时异常.
 *
 *
 *
 */
public abstract class MenuComponent {

    /**
     * 我们把 "组合" 方法 组织在一起,
     * 即 新增, 删除和取得菜单组件.
     */
    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    /**
     * 这些是 "操作" 方法, 它们被菜单项使用,
     * 其中有一些也可用在菜单上.
     */
    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }

    /**
     * print() 方法是一个 "操作" 方法, 这个方法同时被菜单和菜单项所实现,
     * 但还是在这里提供了默认实现.
     */
    public void print() {
        throw new UnsupportedOperationException();
    }

    public Iterator<MenuComponent> createIterator() {
        throw new UnsupportedOperationException();
    }
}
