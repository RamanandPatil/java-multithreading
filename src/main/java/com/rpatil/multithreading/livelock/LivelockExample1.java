package com.rpatil.multithreading.livelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LivelockExample1 {

    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LivelockExample1 livelock = new LivelockExample1();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }

    public void operation1() {
        while (true) {
            tryLock(lock1, 50);
            System.out.println(Thread.currentThread().getName() + ": lock1 acquired, trying to acquire lock2.");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock2.tryLock()) {
                System.out.println(Thread.currentThread().getName() + ": lock2 acquired.");
            } else {
                System.out.println(Thread.currentThread().getName() + ": cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }

            System.out.println(Thread.currentThread().getName() + ": executing first operation.");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }



    public void operation2() {
        while (true) {
            tryLock(lock2, 50);
            System.out.println(Thread.currentThread().getName() + ": lock2 acquired, trying to acquire lock1.");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock1.tryLock()) {
                System.out.println(Thread.currentThread().getName() + ": lock1 acquired.");
            } else {
                System.out.println(Thread.currentThread().getName() + ": cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }

            System.out.println(Thread.currentThread().getName() + ": executing second operation.");

            break;
        }
        lock1.unlock();
        lock2.unlock();
    }


    private void tryLock(Lock lock, long time) {
        try {
            lock.tryLock(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
