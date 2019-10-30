package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 21:32
 */
public class SynchronizedFactory {
    public static void main(String[] args) {
        Unsafe unsafe = new Unsafe();
        IDChecker.test(() -> SyncFactory.factory(unsafe));
    }
}

final class SyncFactory implements HasID {
    private final int id;

    private SyncFactory(SharedArg sa) {
        id = sa.get();
    }

    public static synchronized SyncFactory factory(SharedArg sa) {
        return new SyncFactory(sa);
    }

    @Override
    public int getID() {
        return id;
    }
}
