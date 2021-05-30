package com.qhm.week04.homework;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Thread05 {
    public static void main(String[] args) {
        //先阻塞主线程，等子线程执行完再唤醒
        CyclicBarrier cb=new CyclicBarrier(2);

        Thread thread01=new Thread(()->{
            System.out.println(sum());
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread01.start();
        try {
            cb.await();
        } catch (BrokenBarrierException | InterruptedException e) {
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



