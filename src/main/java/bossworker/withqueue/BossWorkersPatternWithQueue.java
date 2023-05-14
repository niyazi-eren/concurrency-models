package bossworker.withqueue;

import bossworker.Workload;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;

public class BossWorkersPatternWithQueue {
    Shared shared;

    public BossWorkersPatternWithQueue(int numWorkers) {
        shared = new Shared();
        shared.writable = true;
        shared.jobQueue = new ArrayDeque<>();
        shared.lock = new ReentrantLock(true);
        shared.condition = shared.lock.newCondition();

        // init workers
        for (int i = 0; i < numWorkers; i++) {
            WorkerThread workerThread = new WorkerThread(shared);
            workerThread.start();
        }
    }

    public void addWork(int numTasks) {
        while (numTasks >= 0) {
            shared.lock.lock();
            while (!shared.writable) {
                try {
                    shared.condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            shared.jobQueue.add(Workload::work);

            numTasks--;
            shared.writable = false;
            shared.condition.signal();
            shared.lock.unlock();

        }
        System.exit(0);
    }

}

