package com.svute.appsale.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.svute.appsale.R;
import com.svute.appsale.common.StringCommon;
import com.svute.appsale.data.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ogata on 7/28/2022.
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Order> listOrders;
    Context context;

    private OnItemClickOrderHistory onItemClickOrder;

    private int HISTORY_ITEM = 0;
    private int EMPTY_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        Log.d("TAG", "getItemViewType: "+listOrders.get(position));
        if(listOrders.get(position) == null){
            return EMPTY_ITEM;

        }
        return HISTORY_ITEM;

    }

    public HistoryAdapter(){
        listOrders = new ArrayList<>();
    }

    public void updateListOrder(List<Order> data){
        if(listOrders != null && listOrders.size() > 0){
            listOrders.clear();
        }
        listOrders.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == HISTORY_ITEM){
            view = layoutInflater.inflate(R.layout.layout_item_history, parent, false);
            return new HistoryViewHolder(view);

        }else{
            view = layoutInflater.inflate(R.layout.layout_empty_history,parent,false);
            return new emptyViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HistoryViewHolder){
            ((HistoryViewHolder) holder).bind(listOrders.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    class emptyViewHolder extends RecyclerView.ViewHolder {
        public emptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvDay, tvPrice;
        LinearLayout btvDetailCart;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.textView_day);
            tvPrice = itemView.findViewById(R.id.textview_price);
            btvDetailCart = itemView.findViewById(R.id.button_detail_cart_history);

            btvDetailCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickOrder.onClick(getAdapterPosition());
                }
            });

        }

        public void bind(Order order){
            tvDay.setText(StringCommon.formatDate(order.getDate_created()));
            tvPrice.setText("Sum Price: "+ StringCommon.formatCurrency(order.getPrice()));

        }
    }

    public void setOnItemClickOrderHitory(OnItemClickOrderHistory onItemClickOrder){
        this.onItemClickOrder = onItemClickOrder;

    }

    public interface OnItemClickOrderHistory {
        void onClick(int position);

    }
}
