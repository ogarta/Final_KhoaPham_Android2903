package com.svute.appsale.data.repository;

import android.content.Context;

import com.svute.appsale.data.remote.ApiService;
import com.svute.appsale.data.remote.RetrofitClient;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.FoodDTO;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Ogata on 7/25/2022.
 */
public class FoodRepository {
    private ApiService apiService;

    public FoodRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<List<FoodDTO>>> fetchFoods() {
        return apiService.fetchFoods();
    }
}
