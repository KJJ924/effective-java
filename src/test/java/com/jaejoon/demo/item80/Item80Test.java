package com.jaejoon.demo.item80;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * @author dkansk924@naver.com
 * @since 2021/07/27
 */
class Item80Test {


    @Test
    void single() {

        ExecutorService exec = Executors.newSingleThreadExecutor();

        exec.execute(() -> {
            System.out.println("hi");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        exec.execute(() -> System.out.println("Executor"));

    }


    @Test
    void single2() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();

        Future<?> hi = exec.submit(() -> {
            System.out.println("hi");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("나 대기중 ");

        Object o = hi.get();

        System.out.println("기달리는중 ");

        exec.execute(() -> {
            System.out.println("Executor");
        });
    }


    @Test
    void single3() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();

        Future<Integer> submit = exec.submit(() -> {
            System.out.println("hi");

            try {
                Thread.sleep(1000);
                return IntStream.range(0, 10).sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");

            return 0;
        });

        int sum = 0;

        for (int i = 11; i < 20; i++) {
            System.out.println(i);
            if(i == 15){
                exec.shutdown();
            }
            sum += i;
        }

        Integer integer = submit.get();
        System.out.println(integer);
        System.out.println(sum + integer);
    }
}