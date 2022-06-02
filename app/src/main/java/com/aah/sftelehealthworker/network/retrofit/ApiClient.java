package com.aah.sftelehealthworker.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aah.sftelehealthworker.utils.ConstantKt.BASE_URL;

public class ApiClient {
    private static final int REQUEST_TIMEOUT = 5;
    private static Retrofit retrofit = null;

    private static Retrofit getApiClient() {

        if (retrofit == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);

            okHttpClientBuilder
                    .connectTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
                    .readTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
                    .writeTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
                    .build();

            Gson ignoreUnknownProperties = new GsonBuilder().setExclusionStrategies().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(ignoreUnknownProperties))
                    .client(okHttpClientBuilder.build())
                    .build();
        }

        return retrofit;
    }

    public static Api getApi() {
        return ApiClient.getApiClient().create(Api.class);
    }
}
