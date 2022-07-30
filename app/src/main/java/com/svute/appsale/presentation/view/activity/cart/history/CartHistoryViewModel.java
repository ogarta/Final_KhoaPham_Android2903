package com.svute.appsale.presentation.view.activity.cart.history;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.data.remote.dto.OrderDTO;
import com.svute.appsale.data.repository.HistoryRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ogata on 7/30/2022.
 */
public class CartHistoryViewModel extends ViewModel {
    private final HistoryRepository historyRepository;
    private MutableLiveData<AppResource<Order>> orderData = new MutableLiveData<>();
    private int position;

    public CartHistoryViewModel(Context context , int position) {
        historyRepository = new HistoryRepository(context);
        if (orderData == null) {
            orderData = new MutableLiveData<>();
        }
        this.position = position;
    }

    public LiveData<AppResource<Order>> getOrder() { return orderData;}

    public void fetchCartOrderHistory() {
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<List<OrderDTO>>> callHistory = historyRepository.fetchHistory();
        callHistory.enqueue(new Callback<AppResource<List<OrderDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<OrderDTO>>> call, Response<AppResource<List<OrderDTO>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<OrderDTO>> historyResponce = response.body();
                    if (historyResponce.data != null) {
                        orderData.setValue(new AppResource.Success<>(new Order(historyResponce.data.get(position).getId(),
                                historyResponce.data.get(position).getFoods(),
                                historyResponce.data.get(position).getIdUser(),
                                historyResponce.data.get(position).getPrice(),
                                historyResponce.data.get(position).getStatus(),
                                historyResponce.data.get(position).getDate_created())
                        ));
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
            public void onFailure(Call<AppResource<List<OrderDTO>>> call, Throwable t) {
                orderData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

}
