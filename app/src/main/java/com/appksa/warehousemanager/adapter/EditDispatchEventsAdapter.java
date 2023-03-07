package com.appksa.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appksa.warehousemanager.CreateDispatchEventActivity;
import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.model.DispatchEvent;

import java.util.List;

public class EditDispatchEventsAdapter extends RecyclerView.Adapter<EditDispatchEventsAdapter.EditDispatchEventsViewHolder> {

    Context context;
    List<DispatchEvent> editDispatchEventList;

    public EditDispatchEventsAdapter(Context context, List<DispatchEvent> editDispatchEventList) {
        this.context = context;
        this.editDispatchEventList = editDispatchEventList;
    }

    @NonNull
    @Override
    public EditDispatchEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View editDispatchEventsRecyclerItem = LayoutInflater.from(context).inflate(R.layout.edit_dispatch_event_recycler_item, parent, false);
        return new EditDispatchEventsAdapter.EditDispatchEventsViewHolder(editDispatchEventsRecyclerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDispatchEventsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.dispatchEventContractor.setText(editDispatchEventList.get(position).getContractor());
        holder.dispatchEventAmount.setText(String.valueOf(editDispatchEventList.get(position).getAmount()));
        holder.dispatchEventDate.setText(editDispatchEventList.get(position).getDispatchDate());

        holder.dispatchEventEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateDispatchEventActivity.class);

                intent.putExtra("dispatchEventDate", editDispatchEventList.get(position).getDispatchDate());
                intent.putExtra("dispatchEventAmount", editDispatchEventList.get(position).getAmount());
                intent.putExtra("dispatchEventContractor", editDispatchEventList.get(position).getContractor());
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
        return editDispatchEventList.size();
    }

    public static final class EditDispatchEventsViewHolder extends RecyclerView.ViewHolder {

        TextView dispatchEventDate, dispatchEventAmount, dispatchEventContractor, dispatchEventEditButton;

        public EditDispatchEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            dispatchEventContractor = itemView.findViewById(R.id.dispatch_event_recycler_contractor);
            dispatchEventAmount = itemView.findViewById(R.id.dispatch_event_recycler_amount);
            dispatchEventDate = itemView.findViewById(R.id.dispatch_event_recycler_date);
            dispatchEventEditButton = itemView.findViewById(R.id.dispatch_event_recycler_click);

        }
    }
}
