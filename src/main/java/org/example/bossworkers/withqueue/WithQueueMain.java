package org.example.bossworkers.withqueue;

import static org.example.bossworkers.Constants.N_TASKS;
import static org.example.bossworkers.Constants.N_THREADS;

public class WithQueueMain {

    public static void main(String[] args)  {
        var bossWorkersPatternV2 = new BossWorkersPatternWithQueue(N_THREADS);
        bossWorkersPatternV2.addWork(N_TASKS);
    }
}
