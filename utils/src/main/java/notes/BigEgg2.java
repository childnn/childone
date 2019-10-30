package notes;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/13 16:14
 */
public class BigEgg2 extends Egg2 {

    public BigEgg2() {
        super();
        super.insertYolk(new Yolk());
    }

    public static void main(String[] args) {
        Egg2 e2 = new BigEgg2();
        e2.g();
    }

    public class Yolk extends Egg2.Yolk {
        public Yolk() {
            super();
            System.out.println("BigEgg2.Yolk()");
        }

        @Override
        public void f() {
            System.out.println("BigEgg2.Yolk.f()");
        }
    }
}

class Egg2 {
    private Yolk y = new Yolk();

    Egg2() {
        super();
        System.out.println("New Egg2()");
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }

    protected class Yolk {
        public Yolk() {
            super();
            System.out.println("Egg2.Yolk()");
        }

        public void f() {
            System.out.println("Egg2.Yolk.f()");
        }
    }
}