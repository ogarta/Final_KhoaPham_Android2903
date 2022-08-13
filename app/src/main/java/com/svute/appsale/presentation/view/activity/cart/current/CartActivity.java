package com.svute.appsale.presentation.view.activity.cart.current;

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
import com.svute.appsale.common.StringCommon;
import com.svute.appsale.data.model.Food;
import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.presentation.adapter.OrderAdapter;
import com.svute.appsale.presentation.view.activity.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;
    private RecyclerView rcvCart;
    private LinearLayout layoutLoading, btnConfirm;
    private TextView tvSumPrice;
    private OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_cart);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        cartViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderAppResource -> {
            switch (orderAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    orderAdapter.updateListProduct(orderAppResource.data.getFoods());
                    tvSumPrice.setText("Sum Price: "+String.format("%s VND", StringCommon.formatCurrency(orderAppResource.data.getPrice())));
                    setUpdateCart(orderAppResource.data.getId());
                    setConfirmCart(orderAppResource.data.getId());
                    break;
                case ERROR:
                    Toast.makeText(CartActivity.this, orderAppResource.message, Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                    List<Food> foodList = new ArrayList<>();
                    foodList.add(null);
                    orderAdapter.updateListProduct(foodList);
                    btnConfirm.setEnabled(false);
                    break;
            }
        });
    }

    private void setUpdateCart(String idCart) {
        orderAdapter.setOnItemClickOrder((position, quantity) -> cartViewModel.fetchUpdateCart(orderAdapter.getListFoods().get(position).get_id(),idCart,quantity));

    }

    private void setConfirmCart(String id) {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cartViewModel.confirmCart(id);
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(CartActivity.this,"Order Success",Toast.LENGTH_SHORT).show();
        }
        });
    }

    private void events() {
        cartViewModel.fetchCartOrder();

    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);
        btnConfirm = findViewById(R.id.button_confirm);
        tvSumPrice = findViewById(R.id.textview_sum_price);
        cartViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CartViewModel(CartActivity.this);
            }
        }).get(CartViewModel.class);
        orderAdapter = new OrderAdapter();

        // Setup RecyclerView
        rcvCart = findViewById(R.id.recycler_view_cart);
        rcvCart.setAdapter(orderAdapter);
        rcvCart.setHasFixedSize(true);

    }

}