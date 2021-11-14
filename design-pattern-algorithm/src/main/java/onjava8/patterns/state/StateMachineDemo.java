package onjava8.patterns.state;

interface State0 {
    void run();
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 16:57
 */
public class StateMachineDemo {
    public static void main(String[] args) {
        new Washer();
    }
}

abstract class StateMachine {
    protected State0 currentState;

    //Nap(0.5);


    protected abstract boolean changeState();

    // Template method:
    protected final void runAll() {
        while (changeState()) // Customizable
            currentState.run();
    }
}

// A different subclass for each state:
class Wash implements State0 {
    @Override
    public void run() {
        System.out.println("Washing");
    }
}

class Spin implements State0 {
    @Override
    public void run() {
        System.out.println("Spinning");
        //new Nap(0.5);
    }
}

class Rinse implements State0 {
    @Override
    public void run() {
        System.out.println("Rinsing");
        //new Nap(0.5);
    }
}

class Washer extends StateMachine {
    private int i = 0;

    // The state table:
    private State0[] states = {new Wash(), new Spin(), new Rinse(), new Spin(),};

    Washer() {
        runAll();
    }

    @Override
    public boolean changeState() {
        if (i < states.length) {
            // Change the state by setting the
            // surrogate reference to a new object:
            currentState = states[i++];
            return true;
        } else {
            return false;
        }
    }
}