package com.advanced.demo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author by sangw on 2017/8/15.
 */

public interface GitHubClient {
    @GET("top250")
    Call<MovieResponse> getTop250(@Query("start") int start, @Query("count") int count);
}
