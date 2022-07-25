package com.example.appsale29032022.data.repository;

import android.content.Context;

import com.example.appsale29032022.data.model.Food;
import com.example.appsale29032022.data.remote.ApiService;
import com.example.appsale29032022.data.remote.RetrofitClient;
import com.example.appsale29032022.data.remote.dto.AppResource;
import com.example.appsale29032022.data.remote.dto.FoodDTO;

import java.util.List;

import retrofit2.Call;

/**
 * Created by pphat on 7/19/2022.
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
