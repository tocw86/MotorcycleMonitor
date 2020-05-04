package com.software4bikers.motorcyclerun.factory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitFactory {

    Retrofit retrofit;

    public RetrofitFactory() {
        //logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client);
        builder.baseUrl("https://api.openweathermap.org");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
