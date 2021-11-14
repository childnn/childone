package test.basic;

/*
*  private 关键字只能修饰【成员变量】和【成员方法】，不能修饰【类】（private 的类不能被【引用】，没有意义）
* 【提高代码安全性】：只能通过【getter和setter】进行间接访问（可以设置限制条件）
*  对于setter来说，不能有返回值（一般不用自己输出结果，限制条件的提示信息除外），参数类型和相应的【成员变量】一致
*  对于getter来说，不能有参数列表，返回值类型和相应的【成员变量】一致
* */
public class StudentPrivate {

    private int age;
    private String name;
    private boolean male; // 【setMale】和 【isMale】

    public void setMale(boolean b) {
        if (b) {
            male = b;
        } else {
            System.out.println("female");
        }
    }

    public boolean isMale() {
        return male;
    }

      /*  public boolean getMale() { // error, boolean 类型变量的返回形式要写成【is变量名()】
        return male;
    }*/

    public void eat(String str) {
        System.out.println("吃" + str);
    }

    public int sleep() {
        return 8;
    }

    public void setAge(int num) {
//        age = num;
        if (num >= 0 && num <= 100) {
            age = num; // 将 num 赋值给全局变量 age,不要直接输出
        } else {
            System.out.println("你在做梦！");
        }
    }

    public int getAge() {
       return age;
    }

    public void setName(String str) { // 如果这里把 【str】改成【name】(为了见名知意)，则会出现【name = name】这样的式子，这里
                                        // name 默认为这里的【局部变量：也即这里的形参】，也就导致出现 【局部变量】的值 赋值 给
                                                // 【局部变量】自己，这样就无意义。如果一定要将这里的【形参】命名为【name】,
                                        // 就以【this.name = name】这样的格式，这里【this.name】指成员变量【name】,等号右边的【name】
                                        // 指形参【name】
        name = str;
//        System.out.println("姓名：" + str);
    }

    public String getName() {
        return name;
    }

}
