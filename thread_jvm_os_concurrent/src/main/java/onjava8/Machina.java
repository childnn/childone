package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 15:59
 */
public class Machina {
    private final int id;
    private State state = State.START;

    public Machina(int id) {
        this.id = id;
    }

    public static Machina work(Machina m) {
        if (!m.state.equals(State.END)) {
            new Nap(0.1);
            m.state = m.state.step();
        }
        System.out.println(m);
        return m;
    }

    @Override
    public String toString() {
        return "Machina" + id + ": " + (state.equals(State.END) ? "complete" : state);
    }

    public enum State {
        START, ONE, TWO, THREE, END;

        State step() {
            if (equals(END))
                return END;
            return values()[ordinal() + 1];
        }
    }
}

class CompletedMachina {
    public static void main(String[] args) {
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(new Machina(0));
        try {
            Machina m = cf.get();  // Doesn't block
        } catch (InterruptedException |
                ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class CompletableApply {
    public static void main(String[] args) {
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));
        CompletableFuture<Machina> cf2 = cf.thenApply(Machina::work);
        CompletableFuture<Machina> cf3 = cf2.thenApply(Machina::work);
        CompletableFuture<Machina> cf4 = cf3.thenApply(Machina::work);
        CompletableFuture<Machina> cf5 = cf4.thenApply(Machina::work);
    }
}

class CompletableApplyChained {

    public static void main(String[] args) {
        Timer timer = new Timer();
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0))
                .thenApply(Machina::work)
                .thenApply(Machina::work)
                .thenApply(Machina::work)
                .thenApply(Machina::work);
        System.out.println(timer.duration());
    }

}

// 同步调用(我们通常使用得那种)意味着“当你完成工作时，返回”，而异步调用以意味着“立刻返回但是继续后台工作。
class CompletableApplyAsync {
    public static void main(String[] args) {
        Timer timer = new Timer();
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(
                        new Machina(0))
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work);
        System.out.println(timer.duration());
        System.out.println(cf.join());
        System.out.println(timer.duration());
    }
}