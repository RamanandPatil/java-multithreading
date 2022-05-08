package com.rpatil.multithreading.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicSolution {
    public static void main(String[] args) {
        AtomicTask atomicTask = new AtomicTask();

        Thread t1 = new Thread(atomicTask);
        Thread t2 = new Thread(atomicTask);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count is: " + atomicTask.getCount());
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.incrementAndGet();
        }
    }
}


