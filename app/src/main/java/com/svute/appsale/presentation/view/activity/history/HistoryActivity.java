package com.svute.appsale.presentation.view.activity.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svute.appsale.R;
import com.svute.appsale.common.LockScreen;
import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.presentation.adapter.HistoryAdapter;
import com.svute.appsale.presentation.view.activity.cart.history.CartHistoryActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    HistoryViewModel historyViewModel;
    RecyclerView rcvHistory;
    LinearLayout layoutLoading, layoutHistory;
    TextView tvSumPrice;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_history);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        historyViewModel.getHistory().observe(this, (Observer<AppResource<List<Order>>>) historyAppResource -> {
            switch (historyAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    LockScreen.disableLL(layoutHistory,true);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    LockScreen.disableLL(layoutHistory,false);
                    if(historyAppResource.data.size()<=0){
                        layoutLoading.setVisibility(View.GONE);
                        List<Order> orderList = new ArrayList<>();
                        orderList.add(null);
                        historyAdapter.updateListOrder(orderList);
                    }else{
                        historyAdapter.updateListOrder(historyAppResource.data);
                    }
                    historyAdapter.setOnItemClickOrderHitory(position -> openItemCartHistory(position));
                    break;
                case ERROR:
                    Toast.makeText(HistoryActivity.this, historyAppResource.message, Toast.LENGTH_SHORT).show();
                    LockScreen.disableLL(layoutHistory,false);
                    break;
            }
        });
    }

    private void events() {
        historyViewModel.fetchHistory();
    }

    private void openItemCartHistory(int position) {
        Intent intent = new Intent(this, CartHistoryActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    private void addControls() {
        layoutHistory = findViewById(R.id.layout_history);
        layoutLoading = findViewById(R.id.layout_loading);
        tvSumPrice = findViewById(R.id.textview_sum_price);
        historyViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HistoryViewModel(HistoryActivity.this);
            }
        }).get(HistoryViewModel.class);
        historyAdapter = new HistoryAdapter();

        // Setup RecyclerView
        rcvHistory = findViewById(R.id.recycler_view_history);
        rcvHistory.setAdapter(historyAdapter);
        rcvHistory.setHasFixedSize(true);

    }
}