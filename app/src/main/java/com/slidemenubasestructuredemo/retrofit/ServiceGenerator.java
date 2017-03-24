package com.slidemenubasestructuredemo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slidemenubasestructuredemo.Utilities.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(AppConstant.URL).addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(getClient()).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder =new OkHttpClient.Builder();
        if(AppConstant.RETROFIT_LOG_ENABLE) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        builder.connectTimeout(15,TimeUnit.MINUTES);
        builder.readTimeout(15,TimeUnit.MINUTES);
        builder.writeTimeout(15,TimeUnit.MINUTES);
        return builder.build();
    }
}
