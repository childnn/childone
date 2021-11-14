package test.basic;

// 如果一个类中成员方法和成员变量【全都】加了【static】（不含构造方法），则 所有对象公用相同 属性 和行为
public class Static001 {

    private int i;
    int j;
    private static int count; // 把这里的【static】注释掉，看看结果，想想为什么？（不加static，count默认值为0）
    // 无参构造
    public /*static */Static001() { // 不容许在【构造方法】中加【static】修饰，不可以把【public】改为【private】（否则不能被 new 对象，没有意义）
        count++;
    }
    // 有参构造
    public Static001(int i) {
        this.i = i;
        count++;
    }
    // 全参构造
    public Static001(int i, int x) {
        this.i = i;
        j = x; // 也可以写成【this.j = j;】
        count++;
    }

    public int getCount() {
        return count; // 计数对象的个数。
    }

  /*  public Static001(int i, int x,int count) {
        this.count = count;
        System.out.println("ook");
        this.count++;
    }
    */

}
