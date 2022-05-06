package com.rpatil.multithreading.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample2 {
    //creating constructor of the Semaphore with the initial value 3
    static Semaphore semaphore = new Semaphore(3);

    static class DemoThread extends Thread {
        String name = "";

        //constructor of the DemoThread class
        DemoThread(String name) {
            this.name = name;
        }

        public void run() {
            try {
                System.out.println("Thread " + name + " : acquiring lock...");
                System.out.println(
                        "Thread " + name + " : available Semaphore permits is: " + semaphore.availablePermits());
                //thread A acquire lock and the permit count decremented by 1
                semaphore.acquire();
                System.out.println("Thread " + name + " : got the permit!");
                try {
                    for (int i = 1; i <= 5; i++) {
                        System.out.println("Thread " + name + " : is performing operation " + i +
                                           ", available Semaphore permits : " + semaphore.availablePermits());
                        //sleep 2 second
                        Thread.sleep(2000);
                    }
                } finally {
                    System.out.println("Thread " + name + " : releasing lock...");
                    //invoking release() method after successful execution
                    semaphore.release();
                    //prints the total number of available permits
                    System.out.println(
                            "Thread " + name + " : available Semaphore permits is: " + semaphore.availablePermits());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //main method
    public static void main(String[] args) {
        //prints the total number of available permits
        System.out.println("Total available Semaphore permits is: " + semaphore.availablePermits());
        //creating four threads namely A, B, C, and D
        DemoThread t1 = new DemoThread("A");
        //staring thread A
        t1.start();
        DemoThread t2 = new DemoThread("B");
        //staring thread B
        t2.start();
        DemoThread t3 = new DemoThread("C");
        //staring thread C
        t3.start();
        DemoThread t4 = new DemoThread("D");
        //staring thread D
        t4.start();
    }
}
