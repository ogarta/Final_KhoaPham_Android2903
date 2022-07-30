package com.svute.appsale.data.repository;

import android.content.Context;

import com.svute.appsale.data.remote.ApiService;
import com.svute.appsale.data.remote.RetrofitClient;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.OrderDTO;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Ogata on 7/28/2022.
 */
public class HistoryRepository {
    private ApiService apiService;

    public HistoryRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();

    }

    public Call<AppResource<List<OrderDTO>>> fetchHistory() {
        JSONObject jsonObject = new JSONObject();
        return apiService.historyOrder(jsonObject);

    }
}
