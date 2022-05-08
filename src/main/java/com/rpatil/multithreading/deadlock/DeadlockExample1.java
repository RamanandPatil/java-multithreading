package com.rpatil.multithreading.deadlock;

/**
 * <a href="https://www.tutorialspoint.com/java/java_thread_deadlock.htm#:~:text=Deadlock%20occurs%20when%20multiple%20threads,associated%20with%20the%20specified%20object">Java - Thread Deadlock</a>
 */
public class DeadlockExample1 {
    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        ThreadDemo1 T1 = new ThreadDemo1();
        ThreadDemo2 T2 = new ThreadDemo2();
        T1.start();
        T2.start();
    }

    private static class ThreadDemo1 extends Thread {
        public void run() {
            synchronized (LOCK_1) {
                System.out.println("Thread 1: Holding lock 1...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Thread 1: Waiting for lock 2...");

                synchronized (LOCK_2) {
                    System.out.println("Thread 1: Holding lock 1 & 2...");
                }
            }
        }
    }

    private static class ThreadDemo2 extends Thread {
        public void run() {
            synchronized (LOCK_2) {
                System.out.println("Thread 2: Holding lock 2...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Thread 2: Waiting for lock 1...");

                synchronized (LOCK_1) {
                    System.out.println("Thread 2: Holding lock 1 & 2...");
                }
            }
        }
    }
}
