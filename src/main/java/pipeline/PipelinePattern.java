package pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PipelinePattern {
    Queue<Integer> jobQueueStep1;
    Queue<Integer> jobQueueStep2;
    Queue<Integer> jobQueueStep3;
    List<ExecutorService> pipeline;

    public PipelinePattern() {
        pipeline = new ArrayList<>();
        jobQueueStep1 = new ArrayBlockingQueue<>(100);
        jobQueueStep2 = new ArrayBlockingQueue<>(100);
        jobQueueStep3 = new ArrayBlockingQueue<>(100);

        // init threads
        initThread(jobQueueStep1, jobQueueStep2);
        initThread(jobQueueStep2, jobQueueStep3);
        initThread(jobQueueStep3, null);
    }

    public void generateJobs() {
        // generate jobs
        IntStream.range(0, 5).boxed().forEach(n -> jobQueueStep1.add(n));

        // poison pill approach
        jobQueueStep1.add(-1);
    }

    public void initThread(Queue<Integer> queue, Queue<Integer> nextQueue) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        pipeline.add(executorService);
        executorService.submit(() -> {
            while (true) {
                if (!queue.isEmpty()) {
                    var job = queue.poll();

                    // pass the poison pill
                    if (job == -1 && nextQueue != null) nextQueue.add(job);

                    // kill threads
                    else if (job == -1) shutDown();

                    // normal case, process the job
                    else {
                        System.out.println(Thread.currentThread().getName() + " processing job " + job);
                        // job processed, put job in the next queue except for the last thread
                       if (nextQueue != null) nextQueue.add(job);
                    }
                }
            }
        });
    }

    public void shutDown() {
        pipeline.forEach(ExecutorService::shutdown);
        System.exit(0);
    }
}
