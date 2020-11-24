package com.example.tasty.retrofit.config;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitConfig {
    public static final String BASE_URL = "http://192.168.56.1:3333/";

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }
}
