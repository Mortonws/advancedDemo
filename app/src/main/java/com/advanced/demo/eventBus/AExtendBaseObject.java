package com.advanced.demo.eventBus;

/**
 * @author by WuSang on 2019/5/15.
 */
public class AExtendBaseObject extends BaseObject {
    public static void sendMsg() {
        synchronized (BaseObject.class) {
            System.out.println("AExtendBaseObject start sendMsg");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("AExtendBaseObject SendMsg");
        }
    }

//    public static void main(String[] args) {
//        sendMsg();
//    }
}
