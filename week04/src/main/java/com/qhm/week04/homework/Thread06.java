package com.qhm.week04.homework;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Thread06 {
    public static void main(String[] args) {
        //利用阻塞队列为空时会阻塞线程
        BlockingQueue queue = new ArrayBlockingQueue(1);
        Thread thread01=new Thread(()->{
            System.out.println(sum());
            try {
                queue.put("123");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread01.start();
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");


    }
    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
