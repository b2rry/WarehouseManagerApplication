package com.appksa.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.model.DispatchEvent;

import java.util.List;

public class DispatchEventAdapter extends RecyclerView.Adapter<DispatchEventAdapter.DispatchEventViewHolder> {

    Context context;
    List<DispatchEvent> dispatchEventList;

    public DispatchEventAdapter(Context context, List<DispatchEvent> dispatchEventList) {
        this.context = context;
        this.dispatchEventList = dispatchEventList;
    }

    @NonNull
    @Override
    public DispatchEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dispatchEventRecyclerItem = LayoutInflater.from(context).inflate(R.layout.dispatch_event_recycler_item, parent, false);
        return new DispatchEventAdapter.DispatchEventViewHolder(dispatchEventRecyclerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DispatchEventViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.dispatchEventContractor.setText(dispatchEventList.get(position).getContractor());
        holder.dispatchEventAmount.setText(String.valueOf(dispatchEventList.get(position).getAmount()));
        holder.dispatchEventDate.setText(dispatchEventList.get(position).getDispatchDate());

    }

    @Override
    public int getItemCount() {
        return dispatchEventList.size();
    }

    public static final class DispatchEventViewHolder extends RecyclerView.ViewHolder {

        TextView dispatchEventDate, dispatchEventAmount, dispatchEventContractor;

        public DispatchEventViewHolder(@NonNull View itemView) {
            super(itemView);

            dispatchEventContractor = itemView.findViewById(R.id.dispatch_event_recycler_contractor);
            dispatchEventAmount = itemView.findViewById(R.id.dispatch_event_recycler_amount);
            dispatchEventDate = itemView.findViewById(R.id.dispatch_event_recycler_date);

        }
    }
}