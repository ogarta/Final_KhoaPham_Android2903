package com.svute.appsale.presentation.view.activity.cart.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svute.appsale.R;
import com.svute.appsale.common.StringCommon;
import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.presentation.adapter.OrderHistoryAdapter;

public class CartHistoryActivity extends AppCompatActivity {

    private CartHistoryViewModel cartHistoryViewModel;
    private RecyclerView rcvCart;
    private LinearLayout layoutLoading;
    private TextView tvSumPrice, tvDate;
    private int positionOrder;
    private OrderHistoryAdapter orderHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_cart_history);

        addControls();
        observerData();
        getData();
        events();
    }

    private void getData() {
        Intent intent = this.getIntent();
        positionOrder = intent.getIntExtra("position",-1);
        Log.d("TAG", "getData: " + positionOrder);
    }

    private void observerData() {
        cartHistoryViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderHistoryAppResource -> {
            switch (orderHistoryAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    orderHistoryAdapter.updateListProduct(orderHistoryAppResource.data.getFoods());
                    tvDate.setText(StringCommon.formatDate(orderHistoryAppResource.data.getDate_created()));
                    tvSumPrice.setText("Sum Price: "+String.format("%s VND", StringCommon.formatCurrency(orderHistoryAppResource.data.getPrice())));
                    break;
                case ERROR:
                    Toast.makeText(CartHistoryActivity.this, orderHistoryAppResource.message, Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void events() {
        cartHistoryViewModel.fetchCartOrderHistory();

    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);
        tvDate = findViewById(R.id.textview_date_history);
        tvSumPrice = findViewById(R.id.textview_sum_price_history);
        cartHistoryViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CartHistoryViewModel(CartHistoryActivity.this,positionOrder);
            }
        }).get(CartHistoryViewModel.class);
        orderHistoryAdapter = new OrderHistoryAdapter();

        // Setup RecyclerView
        rcvCart = findViewById(R.id.recycler_view_cart_history);
        rcvCart.setAdapter(orderHistoryAdapter);
        rcvCart.setHasFixedSize(true);

    }
}