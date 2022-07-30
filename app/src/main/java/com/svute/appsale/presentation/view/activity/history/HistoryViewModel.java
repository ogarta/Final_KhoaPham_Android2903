package com.svute.appsale.presentation.view.activity.history;

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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ogata on 7/28/2022.
 */
public class HistoryViewModel extends ViewModel {
    private final HistoryRepository historyRepository;
    private MutableLiveData<AppResource<List<Order>>> historyData = new MutableLiveData<>();

    public HistoryViewModel(Context context){
        historyRepository = new HistoryRepository(context);
        if(historyData == null){
            historyData = new MutableLiveData<>();
        }
    }

    public LiveData<AppResource<List<Order>>> getHistory(){return historyData;}

    public void fetchHistory(){
        historyData.setValue(new AppResource.Loading<>(null));
        Call<AppResource<List<OrderDTO>>> callHistory = historyRepository.fetchHistory();
        callHistory.enqueue(new Callback<AppResource<List<OrderDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<OrderDTO>>> call, Response<AppResource<List<OrderDTO>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<OrderDTO>> historyResponce = response.body();

                    if (historyResponce.data != null) {
                        List<Order> listOrder = new ArrayList<>();
                        for (OrderDTO orderDTO : historyResponce.data) {
                            listOrder.add(
                                    new Order(orderDTO.getId(),
                                            orderDTO.getFoods(),
                                            orderDTO.getIdUser(),
                                            orderDTO.getPrice(),
                                            orderDTO.getStatus(),
                                            orderDTO.getDate_created())
                            );
                        }
                        historyData.setValue(new AppResource.Success<>(listOrder));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        historyData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<OrderDTO>>> call, Throwable t) {
                historyData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
