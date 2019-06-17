package com.advanced.demo.rxjava;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author by morton_ws on 2019-06-17.
 */
public class RxPublisherDemo {
    private final static String TAG = RxPublisherDemo.class.getSimpleName();
    private static RxPublisherDemo sInstance;

    private RxPublisherDemo() {

    }

    public static RxPublisherDemo getInstance() {
        if (sInstance == null) {
            synchronized (RxPublisherDemo.class) {
                if (sInstance == null) {
                    sInstance = new RxPublisherDemo();
                }
            }
        }
        return sInstance;
    }

    public FlowableProcessor<String> contentProcess = PublishProcessor.<String>create().toSerialized();

    public Disposable register(Consumer<String> consumer) {
        Flowable<String> flowable = contentProcess.ofType(String.class).observeOn(AndroidSchedulers.mainThread());
        return flowable.subscribe(consumer);
    }

    public void updateContent(String content) {
        contentProcess.onNext(content);
    }
}
