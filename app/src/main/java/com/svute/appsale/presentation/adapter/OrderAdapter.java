package com.svute.appsale.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Ogata on 7/28/2022.
 */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Food> listFoods;
    Context context;
    private OnItemClickOrder onItemClickOrder;

    private int ORDER_ITEM = 0;
    private int EMPTY_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        if(listFoods.get(position) == null){
            return EMPTY_ITEM;
        }
        return ORDER_ITEM;
    }

    public OrderAdapter(){
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == ORDER_ITEM){
        view = layoutInflater.inflate(R.layout.layout_item_order_cart, parent, false);
        return new OrderViewHolder(view);
        }else{
            view = layoutInflater.inflate(R.layout.layout_empty_cart,parent,false);
            return new emptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OrderViewHolder){
            ((OrderViewHolder) holder).bind(context, listFoods.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }
    class emptyViewHolder extends RecyclerView.ViewHolder{

        public emptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvPrice;
        ImageView img;
        TextView txtQuanity;
        LinearLayout btnAddQuanity, btnMinusQuanity;

        public OrderViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewNameOrder);
            tvAddress = view.findViewById(R.id.textViewAddressOrder);
            tvPrice = view.findViewById(R.id.textViewPriceOrder);
            img = view.findViewById(R.id.imageViewOrder);
            btnAddQuanity = view.findViewById(R.id.button_add_quanity);
            btnMinusQuanity = view.findViewById(R.id.button_minus_quanity);
            txtQuanity = view.findViewById(R.id.textViewNumQuanityProduct);

        }

        public void bind(Context context, Food food) {
            int quantity = food.getQuantity();
            tvName.setText(food.getName());
            tvAddress.setText(food.getAddress());
            txtQuanity.setText(quantity+"");
            tvPrice.setText(String.format("%s VND", StringCommon.formatCurrency(food.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.img_logo)
                    .into(img);

            btnAddQuanity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickOrder != null) {
                        onItemClickOrder.onClick(getAdapterPosition(),String.valueOf(quantity+1));
                        Log.d("TAG", "onClick: " +getAdapterPosition());
                    }
                }
            });
            btnMinusQuanity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickOrder != null) {
                        onItemClickOrder.onClick(getAdapterPosition(),String.valueOf(quantity-1));
                    }
                }
            });
        }
    }

    public void setOnItemClickOrder(OnItemClickOrder onItemClickOrder){
        this.onItemClickOrder = onItemClickOrder;
    }

    public interface OnItemClickOrder {
        void onClick(int position, String quantity);
    }
}
