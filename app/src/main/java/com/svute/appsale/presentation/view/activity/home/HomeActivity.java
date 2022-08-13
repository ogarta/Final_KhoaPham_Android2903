package com.svute.appsale.presentation.view.activity.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svute.appsale.R;
import com.svute.appsale.data.local.AppCache;
import com.svute.appsale.data.model.Food;
import com.svute.appsale.data.model.Order;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.presentation.adapter.FoodAdapter;
import com.svute.appsale.presentation.view.activity.cart.current.CartActivity;
import com.svute.appsale.presentation.view.activity.history.HistoryActivity;
import com.svute.appsale.presentation.view.activity.sign_in.SignInActivity;

import java.util.List;

/**
 * Created by Ogata on 7/25/2022.
 */
public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private RecyclerView rcvFood;
    private LinearLayout layoutLoading;
    private FoodAdapter foodAdapter;
    private Toolbar toolBar;
    private TextView tvCountCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        homeViewModel.getFoods().observe(this, new Observer<AppResource<List<Food>>>() {
            @Override
            public void onChanged(AppResource<List<Food>> foodAppResource) {
                switch (foodAppResource.status) {
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        layoutLoading.setVisibility(View.GONE);
                        foodAdapter.updateListProduct(foodAppResource.data);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, foodAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });
        homeViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderAppResource -> {
            switch (orderAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    int quantities = getQuantity(orderAppResource.data == null ? null :  orderAppResource.data.getFoods());
                    setupBadge(quantities);
                    break;
                case ERROR:
                    layoutLoading.setVisibility(View.GONE);
                    break;
            }
        });

        homeViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderAppResource -> {
            switch (orderAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    int quantities = getQuantity(orderAppResource.data == null ? null :  orderAppResource.data.getFoods());
                    setupBadge(quantities);
                    break;
                case ERROR:
                    layoutLoading.setVisibility(View.GONE);
                    break;
            }
        });


    }
    private void events() {
        homeViewModel.fetchFoods();
        homeViewModel.fetchCartOrder();
        foodAdapter.setOnItemClickFood(position -> homeViewModel.fetchOrder(foodAdapter.getListFoods().get(position).get_id()));
    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);
        toolBar = findViewById(R.id.toolbar_home);
        toolBar.setTitle("Food");
        toolBar.setTitleTextColor(getResources().getColor(R.color.primary, null));
        setSupportActionBar(toolBar);

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(HomeActivity.this);
            }
        }).get(HomeViewModel.class);
        foodAdapter = new FoodAdapter();

        // Setup RecyclerView
        rcvFood = findViewById(R.id.recycler_view_food);
        rcvFood.setAdapter(foodAdapter);
        rcvFood.setHasFixedSize(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);
        setupBadge(0);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.action_history:
                Intent intentHistory = new Intent(this, HistoryActivity.class);
                startActivity(intentHistory);
                break;
            case R.id.action_log_out:
                AppCache.getInstance(HomeActivity.this).clear();
                Intent intentLogout = new Intent(this, SignInActivity.class);
                startActivity(intentLogout);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getQuantity(List<Food> listFoods) {
        if (listFoods == null || listFoods.size() == 0) {
            return 0;
        }
        int totalQuantities = 0;
        for (Food food: listFoods) {
            totalQuantities += food.getQuantity();
        }
        return totalQuantities;
    }

    private void setupBadge(int quantities) {
        if (quantities == 0) {
            tvCountCart.setVisibility(View.GONE);
        } else {
            tvCountCart.setVisibility(View.VISIBLE);
            tvCountCart.setText(String.valueOf(Math.min(quantities, 99)));
        }
    }

}