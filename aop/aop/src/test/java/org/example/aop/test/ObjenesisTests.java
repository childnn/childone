package org.example.aop.test;

import org.junit.jupiter.api.Test;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/6/29
 */
public class ObjenesisTests {

    @Test
    void test() {
        ObjenesisStd std = new ObjenesisStd();
        ObjectInstantiator<Xc> instantiatorOf = std.getInstantiatorOf(Xc.class);
        Xc xc = instantiatorOf.newInstance();
        xc.sout();
    }

}

class Xc {

    Xc() {
        throw new RuntimeException("cannot init...");
    }

    public void sout() {
        System.out.println("xxxxxxx");
    }

}
