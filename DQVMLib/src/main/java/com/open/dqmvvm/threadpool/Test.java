package com.open.dqmvvm.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());
        System.out.println("start");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("after 1");

        Future<Object> submit1 = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.currentThread().sleep(3000);
                System.out.println(Thread.currentThread().getName());
                return "hello call";
            }
        });
        System.out.println("after 2");


        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println(submit1.get());
        } catch (Exception e) {}
    }
}
