package com.rpatil.multithreading.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample {
    private static final Semaphore semaphore = new Semaphore(4);

    public static final long TASK_SLEEP_TIME = 5_000;
    private static final boolean useSemaphorePermits = true;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(50);
        if (useSemaphorePermits) {
            IntStream.range(0, 100).forEach(i -> service.execute(new SemaphoreTaskWithDefaultPermits()));
        } else {
            IntStream.range(0, 100).forEach(i -> service.execute(new SemaphoreTaskWithCustomPermits()));
        }

        service.shutdown();
        boolean result = service.awaitTermination(2, TimeUnit.MINUTES);

        if (result) {
            System.out.println("Executor terminated gracefully...");
        } else {
            System.err.println("Timeout occurred before executor termination!!!");
        }

        // if you want to wait till the completion of the whole service:
        // while (!service.isTerminated()) {
        //     // do nothing until service is terminated
        // }

    }

    /**
     * Task with the same permit as the Semaphore
     */
    static class SemaphoreTaskWithDefaultPermits implements Runnable {

        @Override
        public void run() {
            // System.out.println("Running task with " + semaphore.availablePermits() + " permits.");
            // semaphore.acquire(); // throws InterruptedException
            semaphore.acquireUninterruptibly();

            String thread = Thread.currentThread().getName();

            // Do I/O intensive tasks here. Only permitted number of threads are allowed at a time
            // System.out.println("I/O call to the slow service by: " + Thread.currentThread().getName());
            try {
                System.out.println("Doing slow IO task: " + thread);
                Thread.sleep(TASK_SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
                System.out.println(
                        "Released lock by: " + thread + ". " + "Remaining permits: " + semaphore.availablePermits());
            }
        }
    }


    /**
     * Task with the different permit than the Semaphore
     */
    static class SemaphoreTaskWithCustomPermits implements Runnable {

        @Override
        public void run() {
            // System.out.println("Running task with " + semaphore.availablePermits() + " permits.");
            // semaphore.acquire(2); // throws InterruptedException
            semaphore.acquireUninterruptibly(2);

            String thread = Thread.currentThread().getName();

            // Do I/O intensive tasks here. Only permitted number of threads are allowed at a time
            // System.out.println("I/O call to the slow service by: " + Thread.currentThread().getName());
            try {
                System.out.println("Doing slow IO task: " + thread);
                Thread.sleep(TASK_SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release(2);
                System.out.println(
                        "Released lock by: " + thread + ". " + "Remaining permits: " + semaphore.availablePermits());
            }
        }
    }
}
