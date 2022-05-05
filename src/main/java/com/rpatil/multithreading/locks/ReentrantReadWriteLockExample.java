package com.rpatil.multithreading.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    public static final String SHARED_RESOURCE = "SHARED_RESOURCE";
    public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    public static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public static void main(String[] args) {
        Runnable readTask = ReentrantReadWriteLockExample::readResource;
        Runnable writeTask = ReentrantReadWriteLockExample::writeResource;

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(readTask);
            thread.start();
        }

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(writeTask);
            thread.start();
        }

    }

    public static void readResource() {
        readLock.lock();
        try {
            System.out.println("ReentrantLockExample.readResource called by " + Thread.currentThread().getName());
            System.out.println("ReentrantLockExample.readResource: " + SHARED_RESOURCE + Thread.currentThread().getName());
        } finally {
            readLock.unlock();
        }
    }

    public static void writeResource() {
        writeLock.lock();
        try {
            System.out.println("ReentrantLockExample.writeResource called by " + Thread.currentThread().getName());
            System.out.println("ReentrantLockExample.writeResource: " + SHARED_RESOURCE + Thread.currentThread().getName());
        } finally {
            writeLock.unlock();
        }
    }
}
