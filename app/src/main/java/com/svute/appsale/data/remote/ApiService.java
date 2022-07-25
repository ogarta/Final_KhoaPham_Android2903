package com.svute.appsale.data.remote;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ogata on 7/25/2022.
 */
public interface ApiService {

    @POST("user/sign-in")
    Call<AppResource<UserDTO>> signIn(@Body HashMap<String, Object> body);

    @POST("user/sign-up")
    Call<AppResource<UserDTO>> signUp(@Body HashMap<String, Object> body);


}
