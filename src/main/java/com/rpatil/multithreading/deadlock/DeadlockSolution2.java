package com.rpatil.multithreading.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSolution2 {
    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        DeadlockSolution2 deadlock = new DeadlockSolution2();
        new Thread(deadlock::operation1, "Thread-1").start();
        new Thread(deadlock::operation2, "Thread-2").start();
    }

    public void operation1() {
        lock1.lock();
        System.out.println(Thread.currentThread().getName() + ": lock1 acquired, waiting to acquire lock2.");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println(Thread.currentThread().getName() + ": lock2 acquired");

        System.out.println(Thread.currentThread().getName() + ": executing first operation.");

        lock2.unlock();
        lock1.unlock();
    }

    public void operation2() {
        lock1.lock();
        System.out.println(Thread.currentThread().getName() + ": lock1 acquired, waiting to acquire lock2.");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println(Thread.currentThread().getName() + ": lock2 acquired");

        System.out.println(Thread.currentThread().getName() + ": executing second operation.");

        lock2.unlock();
        lock1.unlock();
    }
}
