package test.basic;

public class ClassStandardTest {

    public static void main(String[] args) {
        ClassStandard sta = new ClassStandard();
        sta.setName("缪万");
        sta.setAge(24);
        sta.setB(true);
        System.out.println("姓名：" + sta.getName() + "年龄：" + sta.getAge() + sta.isB());
        System.out.println("=========================");

        ClassStandard sta1 = new ClassStandard("小明",11,true);
        System.out.println("姓名：" + sta1.getName() + "年龄：" + sta1.getAge() + sta1.isB());
    }

}
