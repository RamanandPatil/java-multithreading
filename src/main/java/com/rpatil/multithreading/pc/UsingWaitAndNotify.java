package com.rpatil.multithreading.pc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class UsingWaitAndNotify {
    public static void main(String[] args) {
        MyBasicBlockingQueue<Integer> queue = new MyBasicBlockingQueue<>(10);

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

/**
 * Implementation of the basic blocking queue
 *
 * @param <E> Type of the Queue Elements
 */
class MyBasicBlockingQueue<E> {
    private Queue<E> queue;
    private int maxSize;

    public MyBasicBlockingQueue(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized void put(E e) throws InterruptedException {
        // If size of the queue is full block the thread
        while (queue.size() == maxSize) {
            wait();
        }
        queue.add(e);
        // We have added Item to the queue, so definitely it is not empty.
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        // If size of the queue is 0 (i.e. empty queue) block the thread
        while (queue.size() == 0) {
            wait();
        }
        E item = queue.remove();
        // We have removed an item from the queue, so definitely it is not full.
        notifyAll();
        return item;
    }
}
