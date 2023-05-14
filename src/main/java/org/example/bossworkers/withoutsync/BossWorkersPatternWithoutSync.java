package org.example.bossworkers.withoutsync;

import java.util.HashMap;
import java.util.Map;

public class BossWorkersPatternWithoutSync {

    private final Map<Integer, WorkerThread> workers;

    public BossWorkersPatternWithoutSync(int numThreads) {
        workers = new HashMap<>();
        for (int i = 0; i < numThreads; i++) {
            var worker = new WorkerThread();
            workers.put(i, worker);

            // init workers
            worker.doWork();
        }
    }

    public int getAvailableWorker() {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).workload.isDone()) {
                return i;
            }
        }
        return -1;
    }

    public void giveWork(int workerIndex) {
        workers.get(workerIndex).doWork();
    }

    public void shutDown() {
        for (int i = 0; i < workers.size(); i++) {
            workers.get(i).shutdown();
        }
    }
}
