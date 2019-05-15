package com.advanced.demo.eventBus;

/**
 * @author by WuSang on 2019/5/15.
 */
public class TestObject {
    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread A start");
                AExtendBaseObject.sendMsg();
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread B start");
                BExtendBaseObject.sendMsg();
            }
        });
        threadA.start();
        threadB.start();
    }
}
