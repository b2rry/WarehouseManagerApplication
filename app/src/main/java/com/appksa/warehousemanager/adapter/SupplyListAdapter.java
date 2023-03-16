package com.appksa.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.SupplyItemActivity;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class SupplyListAdapter extends RecyclerView.Adapter<SupplyListAdapter.SupplyListViewHolder>{

    Context context;
    List<SupplyItem> supplyItemList;

    public SupplyListAdapter(Context context, List<SupplyItem> supplyItemList) {
        this.context = context;
        this.supplyItemList = supplyItemList;
    }

    @NonNull
    @Override
    public SupplyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View supplyListRecyclerItem = LayoutInflater.from(context).inflate(R.layout.supply_list_recycler_item, parent, false);
        return new SupplyListAdapter.SupplyListViewHolder(supplyListRecyclerItem);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SupplyListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.supplyItemTitle.setText(supplyItemList.get(position).getTitle());
        holder.supplyItemDate.setText(supplyItemList.get(position).getDate());
        holder.supplyItemStartAmount.setText(String.valueOf(supplyItemList.get(position).getStartAmount()));
        holder.supplyItemRestAvailableAmount.setText(String.valueOf(supplyItemList.get(position).getRestAvailableAmount()));
        if(supplyItemList.get(position).getRestAvailableAmount() < 0){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_attention_red));
        }else{
            int colorId = supplyItemList.get(position).getBgColor();
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, colorId));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SupplyItemActivity.class);

                intent.putExtra("supplyItemId", supplyItemList.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return supplyItemList.size();
    }

    public static final class SupplyListViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView supplyItemTitle, supplyItemDate, supplyItemStartAmount, supplyItemRestAvailableAmount;

        public SupplyListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.supply_item_recycler_card_view);
            supplyItemTitle = itemView.findViewById(R.id.supply_item_recycler_title);
            supplyItemDate = itemView.findViewById(R.id.supply_item_recycler_date);
            supplyItemStartAmount = itemView.findViewById(R.id.supply_item_recycler_start_amount);
            supplyItemRestAvailableAmount = itemView.findViewById(R.id.supply_item_recycler_rest_available_amount);

        }
    }
}