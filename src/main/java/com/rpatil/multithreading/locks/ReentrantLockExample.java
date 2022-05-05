package com.rpatil.multithreading.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    public static final String SHARED_RESOURCE = "SHARED_RESOURCE";
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable task = ReentrantLockExample::accessSharedResource;

        Thread thread1 = new Thread(task);
        thread1.start();

        Thread thread2 = new Thread(task);
        thread2.start();

        Thread thread3 = new Thread(task);
        thread3.start();

        Thread thread4 = new Thread(task);
        thread4.start();
    }

    public static void accessSharedResource() {
        lock.lock();
        try {
            System.out.println("ReentrantLockExample.accessSharedResource called by " + Thread.currentThread().getName());
            System.out.println("Accessing shared resource: " + SHARED_RESOURCE + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }
}
