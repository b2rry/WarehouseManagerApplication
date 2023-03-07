package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.appksa.warehousemanager.adapter.EditDispatchEventsAdapter;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.ArrayList;
import java.util.List;

public class CreateSupplyItemActivity extends AppCompatActivity {

    SupplyItem currentSupplyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_supply_item);

        Long id = getIntent().getLongExtra("supplyItemId", 0);

        currentSupplyItem = getSupplyItemById(id);
        if(currentSupplyItem == null) {
            setTitle(R.string.create_item_message);
        }else{
            setTitle(R.string.edit_item_message);
        }

        EditText editTextTitle = findViewById(R.id.edit_text_title);
        EditText editTextDate = findViewById(R.id.edit_text_date);
        EditText editTextStartAmount = findViewById(R.id.edit_text_start_amount);
        EditText editTextComment = findViewById(R.id.edit_text_comment);

        if(currentSupplyItem != null){
            editTextTitle.setText(currentSupplyItem.getTitle());
            editTextDate.setText(currentSupplyItem.getDate());
            editTextStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
            editTextComment.setText(currentSupplyItem.getComment());
            //izm
        }else{
            currentSupplyItem = new SupplyItem(id, "NAME", "DATE", 0, null, new ArrayList<DispatchEvent>(), "");
            //sozd
        }

        RecyclerView editDispatchEventsRecycler = findViewById(R.id.recycler_edit_dispatch_events);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        editDispatchEventsRecycler.setLayoutManager(layoutManager);

        EditDispatchEventsAdapter editDispatchEventsAdapter = new EditDispatchEventsAdapter(this, currentSupplyItem.getDispatchEventsList());
        editDispatchEventsRecycler.setAdapter(editDispatchEventsAdapter);

        //button click handler

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

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, SupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("supplyItemId", currentSupplyItem.getId());//страховка
        startActivity(intent);
    }

    public void onApplyButtonClick(View view) {
        Intent intent = new Intent(this, SupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("supplyItemId", currentSupplyItem.getId());
        startActivity(intent);
    }

//    public void onAddDispatchEventClick(View view) {
//
//    }
}
//нужно придумать как именно пересоздавать supply_item активность(из supply_list и при возврате из активности для редактирования(наверно стоит узнавать id в onResume а также передавать id в intent при переходе из редакт. активности))