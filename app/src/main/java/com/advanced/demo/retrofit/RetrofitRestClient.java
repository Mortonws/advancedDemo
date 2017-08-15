package com.advanced.demo.retrofit;

import android.util.Log;

import java.util.List;

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
    private final static String API_BASE_URL = "https://api.github.com/";

    private GitHubClient mClient;

    public void createClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        mClient = retrofit.create(GitHubClient.class);
    }

    public void request() {
        Call<List<GitHubRepo>> call = mClient.reposForUser("fs-opensource");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> data = response.body();
                if (data != null) {
                    Log.i(TAG, "response data: " + data.toString());
                } else {
                    Log.w(TAG, "response is null");
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.e(TAG, "response failed", t);
            }
        });
    }
}
