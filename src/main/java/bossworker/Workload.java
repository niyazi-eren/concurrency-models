package bossworker;

public class Workload {

    public static void work() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is working");
        int sum = 0;
        for (int i = 0; i < 10_000_000; i++) {
            sum += i;
        }
    }
}
