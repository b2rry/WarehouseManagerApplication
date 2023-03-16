package com.appksa.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.model.LogBookItem;

public class LogBookItemAdapter extends RecyclerView.Adapter<LogBookItemAdapter.LogBookItemViewHolder>{

    Context context;
    boolean isSave;
    LogBookItem[] logBookItemArray;

    public LogBookItemAdapter(Context context, LogBookItem[] logBookItemArray, boolean isSave) {
        this.context = context;
        this.isSave = isSave;
        this.logBookItemArray = logBookItemArray;
    }

    @NonNull
    @Override
    public LogBookItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View logBookItemRecyclerItem = LayoutInflater.from(context).inflate(R.layout.log_book_item_recycler_item, parent, false);
        return new LogBookItemAdapter.LogBookItemViewHolder(logBookItemRecyclerItem);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull LogBookItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        switch(logBookItemArray[position].getOperationCode()){
            case 0:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_light_grey));
                break;
            case 1:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_blue));
                break;
            case 2:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_purple));
                break;
            case 3:
            case 6:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_green));
                break;
            case 4:
            case 7:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_yellow));
                break;
            case 5:
            case 8:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_background_red));
                break;
            case 400:
                holder.logBookCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.app_custom_attention_red));
                break;
            default:
                break;
        }

        holder.logBookInfoFieldTextView.setText(logBookItemArray[position].getInfoField());
        holder.logBookCommentFieldTextView.setText(logBookItemArray[position].getCommentField());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, LogBookItemActivity.class);
//
//                intent.putExtra("isSave",isSave);
//                intent.putExtra("infoField", logBookItemArray[position].getInfoField());
//                intent.putExtra("commentField", logBookItemArray[position].getCommentField());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return logBookItemArray.length;
    }

    public static final class LogBookItemViewHolder extends RecyclerView.ViewHolder {

        CardView logBookCardView;
        TextView logBookInfoFieldTextView, logBookCommentFieldTextView;

        public LogBookItemViewHolder(@NonNull View itemView) {
            super(itemView);

            logBookCardView = itemView.findViewById(R.id.log_book_recycler_card_view);
            logBookInfoFieldTextView = itemView.findViewById(R.id.log_book_recycler_info_field);
            logBookCommentFieldTextView = itemView.findViewById(R.id.log_book_recycler_comment_field);

        }
    }
}