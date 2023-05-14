package bossworker.withqueue;

public class WorkerThread extends Thread {
    Shared shared;

    public WorkerThread(Shared shared) {
        this.shared = shared;
    }

    @Override
    public void run() {
        while (true) {
            shared.lock.lock();
            while (shared.writable || shared.jobQueue.isEmpty()) {
                try {
                    shared.condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            var job = shared.jobQueue.pop();

            // pretend to do work
            job.run();

            shared.writable = true;
            shared.condition.signal();
            shared.lock.unlock();
        }
    }
}
