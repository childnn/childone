package collection.equalshashcode;

import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 16:26
 */
public class SuccinctEquality extends Equality {
    public SuccinctEquality(int i, String s, double d) {
        super(i, s, d);
        System.out.println("made 'SuccinctEquality'");
    }

    public static void main(String[] args) {
        testAll(SuccinctEquality::new);
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof SuccinctEquality &&
                Objects.equals(i, ((SuccinctEquality) rval).i) &&
                Objects.equals(s, ((SuccinctEquality) rval).s) &&
                Objects.equals(d, ((SuccinctEquality) rval).d);
    }

}
