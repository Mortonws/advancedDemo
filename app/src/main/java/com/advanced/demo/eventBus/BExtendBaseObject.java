package com.advanced.demo.eventBus;

/**
 * @author by WuSang on 2019/5/15.
 */
public class BExtendBaseObject extends BaseObject {
        public static void sendMsg() {
            synchronized (BaseObject.class) {
                System.out.println("BExtendBaseObject start sendMsg");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("BExtendBaseObject SendMsg");
            }
    }
}
