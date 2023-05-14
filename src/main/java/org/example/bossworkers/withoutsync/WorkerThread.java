package org.example.bossworkers.withoutsync;

import org.example.bossworkers.Workload;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WorkerThread {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Future<?> workload;

    public void doWork() {
        // pretend to do some work
        workload = executorService.submit(Workload::work);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
