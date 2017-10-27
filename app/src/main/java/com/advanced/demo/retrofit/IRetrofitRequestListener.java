package com.advanced.demo.retrofit;

/**
 * @author by morton_ws on 2017/10/27.
 */

public interface IRetrofitRequestListener {
    void onSuccess(MovieResponse data);
    void onFailure(Throwable throwable);
    void onError(String error);
}
