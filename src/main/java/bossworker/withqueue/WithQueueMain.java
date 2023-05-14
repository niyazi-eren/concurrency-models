package bossworker.withqueue;

import static bossworker.Constants.N_TASKS;
import static bossworker.Constants.N_THREADS;

public class WithQueueMain {

    public static void main(String[] args)  {
        var bossWorkersPatternV2 = new BossWorkersPatternWithQueue(N_THREADS);
        bossWorkersPatternV2.addWork(N_TASKS);
    }
}
