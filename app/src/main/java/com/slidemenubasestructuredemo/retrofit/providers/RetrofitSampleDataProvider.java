package com.slidemenubasestructuredemo.retrofit.providers;

import com.slidemenubasestructuredemo.retrofit.models.RetrofitSampleSuccess;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitSampleDataProvider {
    @FormUrlEncoded
    @GET("main/samples/{user_id}")
    Call<RetrofitSampleSuccess> getSampleData(@Path("user_id") String userId);
}
