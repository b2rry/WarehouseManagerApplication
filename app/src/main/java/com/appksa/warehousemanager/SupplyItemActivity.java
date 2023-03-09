package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appksa.warehousemanager.adapter.DispatchEventAdapter;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class SupplyItemActivity extends AppCompatActivity {

    SupplyItem currentSupplyItem;
    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_item);

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isChanged = getIntent().getBooleanExtra("isChanged", false);

        currentSupplyItem = getSupplyItemById(id);
        if(currentSupplyItem == null){
            //if here then Error
            setTitle(R.string.error_message);
        }else {
            setTitle(R.string.show_item_message + " позиция с id " + id);

            System.out.println("Create supItem event!");

            TextView textTitle = findViewById(R.id.textView_title);
            TextView textDate = findViewById(R.id.textView_date);
            TextView textStartAmount = findViewById(R.id.textView_start_amount);
            TextView textRestAmount = findViewById(R.id.textView_rest_amount);
            TextView textComment = findViewById(R.id.textView_comment);

            textTitle.setText(currentSupplyItem.getTitle());
            textDate.setText(currentSupplyItem.getDate());
            textStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
            textRestAmount.setText(String.valueOf(currentSupplyItem.getRestAmount()));
            textComment.setText(currentSupplyItem.getComment());

            RecyclerView dispatchEventRecycler = findViewById(R.id.recycler_dispatch_events);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            dispatchEventRecycler.setLayoutManager(layoutManager);

            DispatchEventAdapter dispatchEventAdapter = new DispatchEventAdapter(this, currentSupplyItem.getDispatchEventsList());
            dispatchEventRecycler.setAdapter(dispatchEventAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected SupplyItem getSupplyItemById(Long id){
        List<SupplyItem> currList = MainActivity.warehouseState.getSupplyItemsList();
        for(SupplyItem currItem : currList){
            if(currItem.getId().equals(id)){
                return currItem;
            }
        }
        return null;
    }

    public void onAllPositionsButtonClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        if(!isChanged) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }else{
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    public void onChangeButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("supplyItemId", currentSupplyItem.getId());
        startActivity(intent);
    }
}