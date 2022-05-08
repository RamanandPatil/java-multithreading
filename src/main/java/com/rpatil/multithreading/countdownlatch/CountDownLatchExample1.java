package com.rpatil.multithreading.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample1 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(new Task("Service-1", latch));
        Thread t2 = new Thread(new Task("Service-2", latch));
        Thread t3 = new Thread(new Task("Service-3", latch));

        t1.start();
        t2.start();
        t3.start();

        try {
            latch.await();
            System.out.println("All services are up, starting the main application now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


class Task implements Runnable {
    String service;
    CountDownLatch latch;
    public Task(String service, CountDownLatch latch) {
        this.service = service;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Service " + service + " is up.");
        latch.countDown();
    }
}
