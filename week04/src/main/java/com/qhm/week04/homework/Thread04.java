package com.qhm.week04.homework;


import java.util.concurrent.CountDownLatch;

public class Thread04 {
    public static void main(String[] args) {
        //countDownLatch信号到0时主线程在继续执行
        final CountDownLatch cdl=new CountDownLatch(1);

        Thread thread01=new Thread(()->{
            System.out.println(sum());
            cdl.countDown();
        });
        thread01.start();
        try {
            cdl.await();
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



