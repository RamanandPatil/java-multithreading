package com.rpatil.multithreading.future.completable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5, 9, 14);
        list.stream()
            .map(num -> CompletableFuture.supplyAsync(() -> getNumber(num)))
            .map(CompletableFuture -> CompletableFuture.thenApply(n -> n * n))
            .map(CompletableFuture::join)
            .forEach(System.out::println);
    }

    private static int getNumber(int a) {
        return a * a;
    }
}
