package bossworker.withoutsync;

import static bossworker.Constants.N_TASKS;
import static bossworker.Constants.N_THREADS;

public class WithoutSyncMain {

    public static void main(String[] args) throws InterruptedException {
        var bossWorkers = new BossWorkersPatternWithoutSync(N_THREADS);

        while (N_TASKS > 0) {
            int workerNum = bossWorkers.getAvailableWorker();

            if (workerNum != -1) {
                bossWorkers.giveWork(workerNum);
                N_TASKS--;
            } else {
                Thread.sleep(10);
            }
        }
        bossWorkers.shutDown();
    }
}
