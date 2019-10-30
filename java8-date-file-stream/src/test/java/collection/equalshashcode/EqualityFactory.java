package collection.equalshashcode;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 16:22
 */
public interface EqualityFactory {
    Equality make(int i, String s, double d);
}
