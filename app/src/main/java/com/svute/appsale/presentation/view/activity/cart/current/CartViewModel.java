package com.svute.appsale.presentation.view.activity.cart.current;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.repository.OrderRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ogata on 7/28/2022.
 */
public class CartViewModel extends ViewModel {
    private final OrderRepository oderRepository;
    private MutableLiveData<AppResource<Order>> orderData = new MutableLiveData<>();

    public CartViewModel(Context context) {
        oderRepository = new OrderRepository(context);
        if (orderData == null) {
            orderData = new MutableLiveData<>();
        }
    }

    public LiveData<AppResource<Order>> getOrder() { return orderData;}

    public void fetchCartOrder() {
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> callOrder = oderRepository.fetchCartOrder();
        callOrder.enqueue(new Callback<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>>() {
            @Override
            public void onResponse(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Response<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> response) {
                if (response.isSuccessful()) {
                    AppResource<com.svute.appsale.data.remote.dto.OrderDTO> orderResponse = response.body();

                    if (orderResponse.data != null) {
                        com.svute.appsale.data.remote.dto.OrderDTO orderDTO = orderResponse.data;
                        orderData.setValue(
                                new AppResource.Success(
                                        new Order(
                                                orderDTO.getId(),
                                                orderDTO.getFoods(),
                                                orderDTO.getId(),
                                                orderDTO.getPrice(),
                                                orderDTO.getStatus())));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        orderData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Throwable t) {
                orderData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
    public void fetchUpdateCart(String idFood, String idCart, String quantity) {
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> callOrder = oderRepository.updateCart(idFood,idCart,quantity);
        callOrder.enqueue(new Callback<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>>() {
            @Override
            public void onResponse(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Response<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> response) {
                if (response.isSuccessful()) {
                    AppResource<com.svute.appsale.data.remote.dto.OrderDTO> orderResponse = response.body();

                    if (orderResponse.data != null) {
                        com.svute.appsale.data.remote.dto.OrderDTO orderDTO = orderResponse.data;
                        orderData.setValue(
                                new AppResource.Success(
                                        new Order(
                                                orderDTO.getId(),
                                                orderDTO.getFoods(),
                                                orderDTO.getId(),
                                                orderDTO.getPrice(),
                                                orderDTO.getStatus())));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        orderData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Throwable t) {
                orderData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void confirmCart (String idCart) {
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> callOrder = oderRepository.confirmCart(idCart);
        callOrder.enqueue(new Callback<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>>() {
            @Override
            public void onResponse(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Response<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> response) {
                if (response.isSuccessful()) {
                    orderData.setValue(new AppResource.Success<>(null));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        orderData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> call, Throwable t) {
                orderData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
