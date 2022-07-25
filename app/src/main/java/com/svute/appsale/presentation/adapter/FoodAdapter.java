package com.example.appsale29032022.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsale29032022.R;
import com.example.appsale29032022.common.AppConstant;
import com.example.appsale29032022.common.StringCommon;
import com.example.appsale29032022.data.model.Food;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    List<Food> listFoods;
    Context context;
    private OnItemClickFood onItemClickFood;

    public FoodAdapter() {
        listFoods = new ArrayList<>();
    }

    public void updateListProduct(List<Food> data) {
        if (listFoods != null && listFoods.size() > 0) {
            listFoods.clear();
        }
        listFoods.addAll(data);
        notifyDataSetChanged();
    }

    public List<Food> getListFoods(){
        return listFoods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(context, listFoods.get(position));
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvPrice;
        ImageView img;
        LinearLayout btnAddCart;

        public FoodViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewName);
            tvAddress = view.findViewById(R.id.textViewAddress);
            tvPrice = view.findViewById(R.id.textViewPrice);
            img = view.findViewById(R.id.imageView);
            btnAddCart = view.findViewById(R.id.button_add);

            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickFood != null) {
                        onItemClickFood.onClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Context context, Food food) {
            tvName.setText(food.getName());
            tvAddress.setText(food.getAddress());
            tvPrice.setText(String.format("%s VND", StringCommon.formatCurrency(food.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.img_logo)
                    .into(img);
        }
    }

    public void setOnItemClickFood(OnItemClickFood onItemClickFood){
        this.onItemClickFood = onItemClickFood;
    }

    public interface OnItemClickFood {
        void onClick(int position);
    }
}
