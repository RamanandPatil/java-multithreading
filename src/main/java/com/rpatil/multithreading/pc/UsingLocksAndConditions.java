package com.rpatil.multithreading.pc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UsingLocksAndConditions {
    public static void main(String[] args) {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(1);

        // Producer
        Runnable producer = () -> {
            while (true) {
                try {
                    int item = new Random().nextInt();
                    queue.put(item);
                    System.out.println("Added item: " + item);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(producer).start();
        new Thread(producer).start();

        // Consumer
        Runnable consumer = () -> {
            while (true) {
                try {
                    Integer item = queue.take();
                    System.out.println("Removed item: " + item);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(consumer).start();
        new Thread(consumer).start();
    }

}

class MyBlockingQueue<E> {
    ReentrantLock lock = new ReentrantLock(true);
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    private Queue<E> queue;
    private int maxSize;

    public MyBlockingQueue(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            // If size of the queue is full block the thread
            while (queue.size() == maxSize) {
                notFull.await();
            }
            queue.add(e);
            // We have added Item to the queue, so definitely it is not empty.
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            // If size of the queue is 0 (i.e. empty queue) block the thread
            while (queue.size() == 0) {
                notEmpty.await();
            }
            E item = queue.remove();
            // We have removed an item from the queue, so definitely it is not full.
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
