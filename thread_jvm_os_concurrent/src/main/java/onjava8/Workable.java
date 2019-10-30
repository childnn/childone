package onjava8;

import java.util.concurrent.CompletableFuture;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 18:48
 */
public class Workable {
    final double duration;
    String id;

    public Workable(String id, double duration) {
        this.id = id;
        this.duration = duration;
    }

    public static Workable work(Workable tt) {
        new Nap(tt.duration); // Seconds
        tt.id = tt.id + "W";
        System.out.println(tt);
        return tt;
    }

    public static CompletableFuture<Workable> make(String id, double duration) {
        return CompletableFuture.completedFuture(new Workable(id, duration)).thenApplyAsync(Workable::work);
    }

    @Override
    public String toString() {
        return "Workable[" + id + "]";
    }
}
