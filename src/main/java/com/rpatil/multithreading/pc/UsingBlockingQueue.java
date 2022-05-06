package com.rpatil.multithreading.pc;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class UsingBlockingQueue {
    public static void main(String[] args) {
        // Fixed length BlockingQueue, i.e. ArrayBlockingQueue
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

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
