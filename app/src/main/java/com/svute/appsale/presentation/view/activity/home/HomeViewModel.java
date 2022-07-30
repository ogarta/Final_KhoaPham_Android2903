package com.svute.appsale.presentation.view.activity.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svute.appsale.data.model.Food;
import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.FoodDTO;
import com.svute.appsale.data.repository.FoodRepository;
import com.svute.appsale.data.repository.OrderRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ogata on 7/25/2022.
 */
public class HomeViewModel extends ViewModel {
    private final FoodRepository foodRepository;
    private final OrderRepository oderRepository;
    private MutableLiveData<AppResource<List<Food>>> resourceFood;
    private MutableLiveData<AppResource<Order>> orderData = new MutableLiveData<>();

    public HomeViewModel(Context context) {
        foodRepository = new FoodRepository(context);
        oderRepository = new OrderRepository(context);
        if (resourceFood == null) {
            resourceFood = new MutableLiveData<>();
        }
        if (orderData == null) {
            orderData = new MutableLiveData<>();
        }
    }

    public LiveData<AppResource<List<Food>>> getFoods() {
        return resourceFood;
    }
    public LiveData<AppResource<Order>> getOrder() { return orderData;}

    public void fetchFoods() {
        resourceFood.setValue(new AppResource.Loading(null));
        Call<AppResource<List<FoodDTO>>> callFoods = foodRepository.fetchFoods();
        callFoods.enqueue(new Callback<AppResource<List<FoodDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<FoodDTO>>> call, Response<AppResource<List<FoodDTO>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<FoodDTO>> foodResponse = response.body();
                    if (foodResponse.data != null) {
                        List<Food> listFood = new ArrayList<>();
                        for (FoodDTO foodDTO : foodResponse.data) {
                            listFood.add(
                                    new Food(foodDTO.getId(),
                                            foodDTO.getName(),
                                            foodDTO.getAddress(),
                                            foodDTO.getPrice(),
                                            foodDTO.getImg(),
                                            foodDTO.getQuantity(),
                                            foodDTO.getGallery())
                            );
                        }
                        resourceFood.setValue(new AppResource.Success<>(listFood));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        resourceFood.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<FoodDTO>>> call, Throwable t) {
                resourceFood.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void fetchOrder(String idFood) {
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<com.svute.appsale.data.remote.dto.OrderDTO>> callOrder = oderRepository.addToCart(idFood);
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
}
