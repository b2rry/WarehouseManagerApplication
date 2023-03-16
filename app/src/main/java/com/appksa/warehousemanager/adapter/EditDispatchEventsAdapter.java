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

import com.appksa.warehousemanager.CreateChangeDispatchEventActivity;
import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.model.DispatchEvent;

import java.util.List;

public class EditDispatchEventsAdapter extends RecyclerView.Adapter<EditDispatchEventsAdapter.EditDispatchEventsViewHolder> {

    Context context;
    List<DispatchEvent> editDispatchEventList;
    Long currItemId;

    public EditDispatchEventsAdapter(Context context, List<DispatchEvent> editDispatchEventList, Long currItemId) {
        this.context = context;
        this.editDispatchEventList = editDispatchEventList;
        this.currItemId = currItemId;
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
        holder.cardEditDispatchEvent.setCardBackgroundColor(editDispatchEventList.get(position).isPlaned() ? ContextCompat.getColor(context, R.color.app_custom_dispatch_background_planed) : ContextCompat.getColor(context, R.color.app_custom_dispatch_background_happened));

        holder.dispatchEventEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateChangeDispatchEventActivity.class);

                intent.putExtra("supplyItemId", currItemId);
                intent.putExtra("isNewDispatch", false);
                intent.putExtra("eventId", editDispatchEventList.get(position).getDispatchId());

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
        CardView cardEditDispatchEvent;

        public EditDispatchEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            dispatchEventContractor = itemView.findViewById(R.id.dispatch_event_recycler_contractor);
            dispatchEventAmount = itemView.findViewById(R.id.dispatch_event_recycler_amount);
            dispatchEventDate = itemView.findViewById(R.id.dispatch_event_recycler_date);
            dispatchEventEditButton = itemView.findViewById(R.id.dispatch_event_recycler_edit_click);
            cardEditDispatchEvent = itemView.findViewById(R.id.card_edit_dispatch_event);

        }
    }
}
