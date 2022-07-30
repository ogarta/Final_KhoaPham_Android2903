package com.svute.appsale.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.svute.appsale.R;
import com.svute.appsale.common.AppConstant;
import com.svute.appsale.common.StringCommon;
import com.svute.appsale.data.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ogata on 7/30/2022.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    List<Food> listFoods;
    Context context;

    public OrderHistoryAdapter(){
        listFoods = new ArrayList<>();
    }

    public List<Food> getListFoods(){
        return listFoods;
    }

    public void updateListProduct(List<Food> data) {
        if (listFoods != null && listFoods.size() > 0) {
            listFoods.clear();
        }
        listFoods.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_cart_history, parent, false);
        return new OrderHistoryAdapter.OrderHistoryViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        holder.bind(context, listFoods.get(position));
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPrice;
        ImageView img;
        TextView txtQuanity;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewNameOrderHistory);
            tvAddress = itemView.findViewById(R.id.textViewAddressOrderHistory);
            tvPrice = itemView.findViewById(R.id.textViewPriceOrderHistory);
            img = itemView.findViewById(R.id.imageViewOrderHistory);
            txtQuanity = itemView.findViewById(R.id.textViewNumQuanityProductHistory);
        }

        public void bind(Context context, Food food) {
            int quantity = food.getQuantity();
            tvName.setText(food.getName());
            tvAddress.setText(food.getAddress());
            txtQuanity.setText("Quantity: "+quantity);
            tvPrice.setText(String.format("%s VND", StringCommon.formatCurrency(food.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.img_logo)
                    .into(img);
        }
    }
}
