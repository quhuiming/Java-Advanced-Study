package com.qhm.week04.homework;


public class Thread03 {
    public static void main(String[] args) {
        //主线程判断子线程状态，当子线程销毁后执行
        Thread thread01=new Thread(new Runnable01());
        Object o=new Object();
        thread01.start();
    while (thread01.isAlive()){

    }
        System.out.println("主线程结束");


    }

}
