package onjava8.patterns.proxy;

interface ProxyBase {
    void f();

    void g();

    void h();
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 16:44
 */
public class ProxyDemo {

    public static void main(String[] args) {
        Proxy p = new Proxy();
        p.f();
        p.g();
        p.h();
    }

}

class Proxy implements ProxyBase {
    private ProxyBase implementation;

    Proxy() {
        implementation = new Implementation();
    }

    // Pass method calls to the implementation:
    @Override
    public void f() {
        implementation.f();
    }

    @Override
    public void g() {
        implementation.g();
    }

    @Override
    public void h() {
        implementation.h();
    }
}

class Implementation implements ProxyBase {
    public void f() {
        System.out.println("Implementation.f()");
    }

    public void g() {
        System.out.println("Implementation.g()");
    }

    public void h() {
        System.out.println("Implementation.h()");
    }
}