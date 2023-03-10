package com.appksa.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.appksa.warehousemanager.SupplyListActivity;
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
        holder.supplyItemRestAmount.setText(String.valueOf(supplyItemList.get(position).getRestAmount()));
        if(supplyItemList.get(position).getRestAmount() < 0){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.attention_red));
        }else{
            int colorId = supplyItemList.get(position).getBgColor();
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, colorId));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SupplyItemActivity.class);

                intent.putExtra("supplyItemId", supplyItemList.get(position).getId());
//                intent.putExtra("supplyItemTitle", supplyItemList.get(position).getTitle());
//                intent.putExtra("supplyItemDate", supplyItemList.get(position).getDate());
//                intent.putExtra("supplyItemStartAmount", supplyItemList.get(position).getStartAmount());
//                intent.putExtra("supplyItemRestAmount", supplyItemList.get(position).getRestAmount());
//                intent.putExtra("supplyItemBgColor", Color.parseColor(supplyItemList.get(position).getBgColor()));
//                intent.putExtra("supplyItemComment", supplyItemList.get(position).getComment());
//                intent.putExtra("supplyItemDispatchEventsList", supplyItemList.get(position).getDispatchEventsList());
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
        TextView supplyItemTitle, supplyItemDate, supplyItemStartAmount, supplyItemRestAmount;

        public SupplyListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.supply_item_recycler_card_view);
            supplyItemTitle = itemView.findViewById(R.id.supply_item_recycler_title);
            supplyItemDate = itemView.findViewById(R.id.supply_item_recycler_date);
            supplyItemStartAmount = itemView.findViewById(R.id.supply_item_recycler_start_amount);
            supplyItemRestAmount = itemView.findViewById(R.id.supply_item_recycler_rest_amount);

        }
    }
}