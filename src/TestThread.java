import DAO.Database;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class TestThread {
    public static void main(String[] args) {
        Database db = new Database();
        ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicReference<Double>>> accounts = db.getAccounts();

        // Simulating concurrent deposit and withdraw actions
        ExecutorService executor = Executors.newFixedThreadPool(4);  // Create a thread pool with 4 threads

        // Test case 1: Deposit in Account 1 for User 1
        Runnable depositTask1 = () -> {
            System.out.println(Thread.currentThread().getName()+"Is going to deposit 200");
            System.out.println(Thread.currentThread().getName()+" :"+db.deposit("u1", "a1",200));
            System.out.println(Thread.currentThread().getName()+" :"+db.checkBalance("u1", "a1"));
            System.out.println(Thread.currentThread().getName()+"Is done with deposit 200");

        };

        // Test case 2: Withdraw from Account 1 for User 1
        Runnable withdrawTask1 = () -> {
            System.out.println(Thread.currentThread().getName()+"Is going to withdraw 100");
            System.out.println(Thread.currentThread().getName()+" :"+db.withdraw("u1", "a1",100));
            System.out.println(Thread.currentThread().getName()+" :"+db.checkBalance("u1", "a1"));
            System.out.println(Thread.currentThread().getName()+"Is done withdraw 100");
        };

        // Test case 3: Deposit in Account 2 for User 2
        Runnable depositTask2 = () -> {
            System.out.println(Thread.currentThread().getName()+"Is going to deposit 100");
            System.out.println(Thread.currentThread().getName()+" :"+db.deposit("u1", "a1",100));
            System.out.println(Thread.currentThread().getName()+" :"+db.checkBalance("u1", "a1"));
            System.out.println(Thread.currentThread().getName()+"Is done with deposit 100");

        };

        // Test case 4: Withdraw from Account 3 for User 3 (insufficient funds scenario)
        Runnable withdrawTask3 = () -> {
            System.out.println(Thread.currentThread().getName()+"Is going to withdraw 200");
            System.out.println(Thread.currentThread().getName()+" :"+db.withdraw("u1", "a1",200));
            System.out.println(Thread.currentThread().getName()+" :"+db.checkBalance("u1", "a1"));
            System.out.println(Thread.currentThread().getName()+"Is done with withdraw 200");

        };

        // Submit tasks for execution
        executor.submit(depositTask1);
        executor.submit(withdrawTask1);
        executor.submit(depositTask2);
        executor.submit(withdrawTask3);

        // Wait for all threads to finish execution
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
