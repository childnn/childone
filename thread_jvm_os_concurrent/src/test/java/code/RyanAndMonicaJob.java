package code;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/24 8:45
 */
public class RyanAndMonicaJob implements Runnable {

    private BankAccount account = new BankAccount();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            makeWithdrawal(10);
            // 检查账户余额, 如果透支就列出信息.
            // 不然就去睡一会儿, 然后醒来完成提款操作.
            if (account.getBalance() < 0) {
                System.out.println("Overdrawn!");
            }
        }
    }

    // 同步, 原子性.
    private synchronized void makeWithdrawal(int amount) {
        if (account.getBalance() >= amount) {
            // 检查账户余额, 如果透支就列出信息, 不然就去睡一会儿, 然后醒来完成提款操作.
            System.out.println(Thread.currentThread().getName() + " is about to withdraw");
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " woke up.");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes the withdrawal");
        } else {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        RyanAndMonicaJob theJob = new RyanAndMonicaJob(); // 初始化任务.
        // 创建出使用相同任务的两个线程, 这代表两个线程都会存取同一账户.
        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);
        one.setName("Ryan");
        two.setName("Monica");
        one.start();
        two.start();
    }

}

class BankAccount {
    private int balance = 100; // 账户一开始只有 100 元.

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        balance = balance - amount;
    }
}
