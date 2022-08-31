package com.rpatil.multithreading.atomic;

public class NonAtomicProblem {
    public static void main(String[] args) {
        // Common counterTask which will be shared between 2 or more threads
        CounterTask counterTask = new CounterTask();

        // Create threads with shared counterTask
        Thread thread1 = new Thread(counterTask);
        Thread thread2 = new Thread(counterTask);

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for all the threads to complete there execution
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Since both threads thread1 and thread2 were working on a same counterTask
        // (i.e. shared resource) it is expected that the counter value should be 100.
        // But this happens very rarely because of the concurrency issue, we always get less value.
        if (counterTask.getCount() != 100) {
            System.err.print("Incorrect result, count value should be 100, but ");
            System.err.println("Count value is: " + counterTask.getCount());
        } else {
            System.out.println("Correct result, count value is 100");
        }
    }
}

class CounterTask implements Runnable {

    private int count;

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }
}
