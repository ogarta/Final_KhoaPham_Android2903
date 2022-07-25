package com.svute.appsale.data.repository;

import android.content.Context;

import com.svute.appsale.data.remote.ApiService;
import com.svute.appsale.data.remote.RetrofitClient;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by Ogata on 7/25/2022.
 */
public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<UserDTO>> signIn(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return apiService.signIn(map);
    }

    public Call<AppResource<UserDTO>> signUp(String email, String password, String name, String phone, String address) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("phone", phone);
        map.put("address", address);
        return apiService.signUp(map);
    }
}
