package com.advanced.demo.retrofit;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by sangw on 2017/8/15.
 */

public class RetrofitRestClient {
    private final static String TAG = "RetrofitClient";
    private final static String API_BASE_URL = "https://api.douban.com/v2/movie/";

    private GitHubClient mClient;
    private IRetrofitRequestListener mRequestListener;

    public void createClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        mClient = retrofit.create(GitHubClient.class);
    }

    public void request(int start, int count) {
        Call<MovieResponse> call = mClient.getTop250(start, count);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse data = response.body();
                if (data != null) {
                    Log.i(TAG, "response data: " + data.toString());
                    if (mRequestListener != null) {
                        mRequestListener.onSuccess(data);
                    }
                } else {
                    Log.w(TAG, "response is null");
                    if (mRequestListener != null) {
                        mRequestListener.onError("response is null");
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "response failed", t);
                if (mRequestListener != null) {
                    mRequestListener.onFailure(t);
                }
            }
        });
    }

    public void addRequestListener(IRetrofitRequestListener requestListener) {
        this.mRequestListener = requestListener;
    }
}
