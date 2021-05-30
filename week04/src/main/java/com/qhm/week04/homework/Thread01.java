package com.qhm.week04.homework;


public class Thread01 {
    public static void main(String[] args) {
        //子线程调用join方法插队
        Thread thread01=new Thread(new Runnable01());
        thread01.start();
        try {
            thread01.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");


    }

}
