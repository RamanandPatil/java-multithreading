package com.rpatil.multithreading.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicSolution {
    public static void main(String[] args) {
        // Common counterTask which will be shared between 2 or more threads
        AtomicTask atomicCounterTask = new AtomicTask();

        // Create threads with shared atomicCounterTask
        Thread thread1 = new Thread(atomicCounterTask);
        Thread thread2 = new Thread(atomicCounterTask);

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

        // Since both threads thread1 and thread2 were working on a same atomicCounterTask
        // (i.e. shared resource) it is expected that the counter value should be 100.
        // Here,this happens always because counter uses atomic operation using AtomicInteger.
        if (atomicCounterTask.getCount() != 100) {
            System.err.print("Incorrect result, count value should be 100, but ");
            System.err.println("Count value is: " + atomicCounterTask.getCount());
        } else {
            System.out.println("Correct result, count value is 100");
        }
    }
}

class AtomicTask implements Runnable {

    private AtomicInteger count = new AtomicInteger();

    public int getCount() {
        return count.get();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.incrementAndGet();
        }
    }
}


