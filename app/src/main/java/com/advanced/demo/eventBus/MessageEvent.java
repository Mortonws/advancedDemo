package com.advanced.demo.eventBus;

/**
 * @author by WuSang on 2019/5/9.
 */
public class MessageEvent {
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "mMessage='" + mMessage + '\'' +
                '}';
    }
}
