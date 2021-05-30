package com.qhm.week04.homework;


public class Thread02 {
    public static void main(String[] args) {
        //主线程调用sleep()暂停等待子线程执行
        Thread thread01=new Thread(new Runnable01());
        thread01.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");


    }

}
