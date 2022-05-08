package com.rpatil.multithreading.deadlock;

public class DeadlockSolution1 {
    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        ThreadDemo1 T1 = new DeadlockSolution1.ThreadDemo1();
        ThreadDemo2 T2 = new DeadlockSolution1.ThreadDemo2();
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
            synchronized (LOCK_1) {
                System.out.println("Thread 2: Holding lock 1...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Thread 2: Waiting for lock 2...");

                synchronized (LOCK_2) {
                    System.out.println("Thread 2: Holding lock 1 & 2...");
                }
            }
        }
    }
}
