package com.svute.appsale.data.remote;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svute.appsale.common.AppConstant;
import com.svute.appsale.data.local.AppCache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ogata on 7/25/2022.
 */
public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitClient(Context context) {
        retrofit = createRetrofit(context);
        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    /**
     * Create Instance Retrofit
     * @return Retrofit
     */
    private Retrofit createRetrofit(Context context) {
        HttpLoggingInterceptor logRequest = new HttpLoggingInterceptor();
        logRequest.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logRequest)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        String token = (String) AppCache.getInstance(context).getValue(AppConstant.TOKEN_KEY);
                        if (token != null && !token.isEmpty()) {
                            Request newRequest  = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                        return chain.proceed(chain.request());
                    }
                })
                .build();

        Gson gson = new GsonBuilder().create();

        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Get Interface End Point
     * @return ApiService
     */
    public ApiService getApiService(){
        if (apiService != null) {
            return apiService;
        } else {
            return retrofit.create(ApiService.class);
        }
    }
}
